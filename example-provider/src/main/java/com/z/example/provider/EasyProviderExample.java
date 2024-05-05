package com.z.example.provider;

import com.z.rpc.server.HttpServer;
import com.z.rpc.server.VertxHttpServer;

/**
 * @Author: Citres
 * @Description:
 * @Date: 2024/5/5 17:05
 */

@SuppressWarnings({"all"})
/**
 * 简易服务提供者示例
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8080);
    }
}
