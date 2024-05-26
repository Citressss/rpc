package com.z.springboot.consumer;


import com.z.example.common.model.User;
import com.z.example.common.service.UserService;
import com.z.rpcspringbootstarter.annotation.RpcReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @RpcReference
    UserService userService;

    /**
     * 测试
     * @param name
     * @return
     */
    @GetMapping("/getUserName")
    public String getUser(@RequestParam String name) {
        User user = new User();
        user.setName(name);
        String res = userService.getUser(user).getName();
        return res;
    }
}
