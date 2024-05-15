package com.z.example.provider;

import com.z.example.common.service.UserService;
import com.z.rpc.RpcApplication;
import com.z.rpc.registry.LocalRegistry;
import com.z.rpc.server.HttpServer;
import com.z.rpc.server.VertxHttpServer;


@SuppressWarnings({"all"})
/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        RpcApplication.init();
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
