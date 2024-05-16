package com.z.example.consumer;

import com.z.example.common.model.User;
import com.z.example.common.service.UserService;
import com.z.rpc.proxy.ServiceProxyFactory;

@SuppressWarnings({"all"})
/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // TODO 需要获取 UserService 的实现类对象
//        UserService userService = new UserServiceProxy();
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("zzh");
        // 调用
        User newUser = userService.getUser(user);
        User testCacheuser1 = userService.getUser(user);
        User testCacheuser2 = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
