package com.z.example.provider;

import com.z.example.common.model.User;
import com.z.example.common.service.UserService;

/**
 * @Author: Citres
 * @Description:
 * @Date: 2024/5/5 17:04
 */

@SuppressWarnings({"all"})
/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
