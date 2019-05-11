package com.qwwaq.cyb.service;

import com.alibaba.fastjson.JSON;
import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.User;
import com.qwwaq.cyb.service.api.UserService;

import com.qwwaq.cyb.service.mapper.ArticleMapper;
import com.qwwaq.cyb.service.mapper.ProjectMapper;
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

    @Resource
    ProjectMapper projectMapper;

    @Resource
    ArticleMapper articleMapper;

    @Test
    public void contextLoads() {
        System.out.println(JSON.toJSONString(articleMapper.queryArticleWithModule("ss")));

        System.out.println(JSON.toJSONString(projectMapper.queryArticleWithModule("ss")));
    }

}
