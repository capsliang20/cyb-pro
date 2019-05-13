package com.qwwaq.cyb.service;

import com.alibaba.fastjson.JSON;
import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Comment;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.User;
import com.qwwaq.cyb.service.api.ArticleService;
import com.qwwaq.cyb.service.api.CommentService;
import com.qwwaq.cyb.service.api.ProjectService;
import com.qwwaq.cyb.service.api.UserService;

import com.qwwaq.cyb.service.mapper.ArticleMapper;
import com.qwwaq.cyb.service.mapper.CommentMapper;
import com.qwwaq.cyb.service.mapper.ProjectMapper;
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

    @Test
    public void contextLoads() {
       Article article=articleService.queryArticleDetail(1);
        System.out.println(JSON.toJSONString(article));

        Project project=projectService.queryProjectDetail(1);
        System.out.println(JSON.toJSONString(project));
        
    }

}
