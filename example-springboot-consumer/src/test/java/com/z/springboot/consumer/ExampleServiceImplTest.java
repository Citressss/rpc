package com.z.springboot.consumer;

import com.z.example.common.model.User;
import com.z.example.common.service.UserService;
import com.z.rpcspringbootstarter.annotation.RpcReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ExampleServiceImplTest {

    @Resource
    private ExampleServiceImpl exampleService;
    @RpcReference
    private UserService userService;

    @Test
    void test1() {
        exampleService.test();
    }

    @Test
    void testLocalCache() {
        int REQUEST_TIME = 1_000;
        long begin = System.currentTimeMillis();
        System.out.println(begin);
        for (int i = 0; i < REQUEST_TIME; i++) {
            User user = new User();
            user.setName("testCache");
            User resultUser = userService.getUser(user);
//            System.out.println(resultUser.getName());
        }
        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println(end - begin);
        System.out.println("用时 : " + (end - begin) / 1000 + "秒");
    }
}