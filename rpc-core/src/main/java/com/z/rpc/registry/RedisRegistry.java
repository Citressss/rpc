package com.z.rpc.registry;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.core.util.StrUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.z.rpc.config.RegistryConfig;
import com.z.rpc.model.ServiceMetaInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Redis 注册中心
 */
public class RedisRegistry implements Registry {

    private Jedis jedis;

    /**
     * 本机注册的节点 key 集合（用于维护续期）
     */
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();

    /**
     * 注册中心服务缓存
     */
    private final RegistryServiceCache registryServiceCache = new RegistryServiceCache();

    /**
     * 正在监听的 key 集合
     */
    private final Set<String> watchingKeySet = new ConcurrentHashSet<>();

    /**
     * 根节点
     */
    private static final String REDIS_ROOT_PATH = "rpc:";

    @Override
    public void init(RegistryConfig registryConfig) {
        jedis = new Jedis(registryConfig.getAddress(), registryConfig.getPort());
        jedis.auth(registryConfig.getPassword());
        heartBeat();
    }

    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        String registerKey = REDIS_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        jedis.setex(registerKey, 30, JSONUtil.toJsonStr(serviceMetaInfo));

        localRegisterNodeKeySet.add(registerKey);
    }

    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        String registerKey = REDIS_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        jedis.del(registerKey);
        // 本地缓存移除
        localRegisterNodeKeySet.remove(registerKey);
    }

    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        // 优先从缓存获取服务
        List<ServiceMetaInfo> cachedServiceMetaInfoList = registryServiceCache.readCache();
        if (!CollUtil.isEmpty(cachedServiceMetaInfoList)) {
            return cachedServiceMetaInfoList;
        }
        // 前缀搜索，结尾一定要加 '/'
        String searchPrefix = REDIS_ROOT_PATH + serviceKey + "/";

        try {
            // 前缀查询
            String cursor = "0";
            String match = searchPrefix + "*";
//            ScanParams params = new ScanParams().match(match);
//            List<String> keys = jedis.scan(cursor, params).getResult();
            Set<String> keys = jedis.keys(match);
            // 解析服务信息
            List<ServiceMetaInfo> serviceMetaInfoList = keys.stream()
                    .map(key -> {
                        // 监听 key 的变化
                        watch(key);
                        String value = jedis.get(key);
                        return JSONUtil.toBean(value, ServiceMetaInfo.class);
                    })
                    .collect(Collectors.toList());
            // 写入服务缓存
            registryServiceCache.writeCache(serviceMetaInfoList);
            return serviceMetaInfoList;
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    @Override
    public void destroy() {
        System.out.println("当前节点下线");
        // 释放资源

        // 遍历本节点所有的 key
        for (String key : localRegisterNodeKeySet) {
            try {
                jedis.del(key);
            } catch (Exception e) {
                throw new RuntimeException(key + "节点下线失败");
            }
        }
        if (jedis != null) jedis.close();
    }

    @Override
    public void heartBeat() {
        // 10 秒续签一次
        CronUtil.schedule("*/10 * * * * *", (Task) () -> {
            // 遍历本节点所有的 key
            for (String key : localRegisterNodeKeySet) {
                try {
                    String value = jedis.get(key);
                    // 该节点已过期（需要重启节点才能重新注册）
                    if (StrUtil.isEmpty(value)) continue;
                    // 节点未过期，重新注册（相当于续签）
                    ServiceMetaInfo serviceMetaInfo = JSONUtil.toBean(value, ServiceMetaInfo.class);
                    register(serviceMetaInfo);
                } catch (Exception e) {
                    throw new RuntimeException(key + "续签失败", e);
                }
            }
        });
        // 支持秒级别定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }

    @Override
    public void watch(String serviceNodeKey) {
        // 之前未被监听，开启监听
        boolean newWatch = watchingKeySet.add(serviceNodeKey);
        if (newWatch) {
            // 注册监听器
            class KeyChangeListener extends JedisPubSub {
                @Override
                public void onMessage(String channel, String message) {
                    if (channel.equals(serviceNodeKey)) {
                        // 清理缓存
                        registryServiceCache.clearCache();
                    }
                }
            }
            KeyChangeListener listener = new KeyChangeListener();
            // TODO redis键下线通知缓存清理
        }
    }
}
