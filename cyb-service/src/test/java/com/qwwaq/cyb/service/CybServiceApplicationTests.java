package com.qwwaq.cyb.service;

import com.qwwaq.cyb.service.api.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CybServiceApplicationTests {

    @Resource
    UserService userService;

    @Test
    public void contextLoads() {
        System.out.println(userService.queryUser(1));
    }

}
