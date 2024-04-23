package com.z.example.common.service;

import com.z.example.common.model.User;

/**
 * @Author: Citres
 * @Description:
 * @Date: 2024/4/23 21:08
 */

@SuppressWarnings({"all"})
/**
 * 用户服务
 */
public interface UserService {
    /**
     * 获取用户
     *
     * @param user
     * @return
     */
    User getUser(User user);
}
