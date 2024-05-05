package com.z.rpc.server;

/**
 * @Author: Citres
 * @Description:
 * @Date: 2024/5/5 17:32
 */

@SuppressWarnings({"all"})

/**
 * HTTP 服务器接口
 */
public interface HttpServer {
    /**
     * 启动服务器
     *
     * @param port
     */
    void doStart(int port);
}
