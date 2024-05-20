package com.z.springboot.consumer;

import com.z.example.common.model.User;
import com.z.example.common.service.UserService;
import com.z.rpcspringbootstarter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("zzh");
        User resultUser = userService.getUser(user);
        System.out.println(resultUser.getName());
    }

}