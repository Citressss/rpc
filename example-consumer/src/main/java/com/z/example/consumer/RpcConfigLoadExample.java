package com.z.example.consumer;

import com.z.rpc.config.RpcConfig;
import com.z.rpc.utils.ConfigUtils;

/**
 * 简易服务消费者示例
 */
public class RpcConfigLoadExample {

    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
