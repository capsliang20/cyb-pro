package com.qwwaq.cyb.service;

import com.alibaba.fastjson.JSON;
import com.qwwaq.cyb.entity.*;
import com.qwwaq.cyb.service.api.*;

import com.qwwaq.cyb.service.mapper.ArticleMapper;
import com.qwwaq.cyb.service.mapper.CommentMapper;
import com.qwwaq.cyb.service.mapper.FollowerMapper;
import com.qwwaq.cyb.service.mapper.ProjectMapper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CybServiceApplicationTests {

    @Resource
    UserService userService;

    @Resource
    ProjectMapper projectMapper;

    @Resource
    ArticleMapper articleMapper;
    
    @Resource
    ArticleService articleService;

    @Resource
    CommentMapper commentMapper;

    @Resource
    CommentService commentService;

    @Resource
    ProjectService projectService;

    @Resource
    FollowerMapper followerMapper;

    @Resource
    RecommendService recommendService;


    @Test
    public void contextLoads() {
        System.out.println(JSON.toJSONString(recommendService.recommend(Recommend.USER_RECOMMEND, 1)));
    }

}
