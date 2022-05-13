package com.ledao;

import com.ledao.entity.User;
import com.ledao.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class BookManageSystemApplicationTests {


    @Resource
    private UserService userService;

    @Test
    public void addUser(){
        User user = new User();
        user.setUserName("tom");
        user.setNickName("LeDao");
        user.setPassword("123456");
        userService.add(user);
        System.out.println("自增的id为: "+user.getId());
    }
}