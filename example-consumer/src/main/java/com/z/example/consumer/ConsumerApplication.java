package com.z.example.consumer;

import com.z.example.common.model.User;
import com.z.example.common.service.UserService;
import com.z.rpc.bootstrap.ConsumerBootstrap;
import com.z.rpc.proxy.ServiceProxyFactory;

/**
 * 服务提供者示例
 */
public class ConsumerApplication {

    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("zzh");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}