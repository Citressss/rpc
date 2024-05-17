package com.z.example.provider;

import com.z.example.common.service.UserService;
import com.z.rpc.RpcApplication;
import com.z.rpc.config.RegistryConfig;
import com.z.rpc.config.RpcConfig;
import com.z.rpc.model.ServiceMetaInfo;
import com.z.rpc.registry.LocalRegistry;
import com.z.rpc.registry.Registry;
import com.z.rpc.registry.RegistryFactory;
import com.z.rpc.server.tcp.VertxTcpServer;

/**
 * 服务提供者示例
 */
public class TcpProviderExample {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 TCP 服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(8080);
    }
}