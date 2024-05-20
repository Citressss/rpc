package com.z.example.provider;

import com.z.example.common.model.User;
import com.z.example.common.service.UserService;


@SuppressWarnings({"all"})
/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
//        throw new RuntimeException("测试Exception");
        return user;
    }
}
