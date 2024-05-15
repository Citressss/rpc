package com.z.example.common.service;

import com.z.example.common.model.User;


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

    /**
     * 获取数字
     */
    default short getNumber() {
        return 1;
    }
}
