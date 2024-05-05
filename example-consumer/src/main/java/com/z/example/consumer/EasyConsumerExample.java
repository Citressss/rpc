package com.z.example.consumer;

import com.z.example.common.model.User;
import com.z.example.common.service.UserService;

/**
 * @Author: Citres
 * @Description:
 * @Date: 2024/5/5 17:22
 */

@SuppressWarnings({"all"})
/**
 * 简易服务消费者示例
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // TODO 需要获取 UserService 的实现类对象
        UserService userService = null;
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
