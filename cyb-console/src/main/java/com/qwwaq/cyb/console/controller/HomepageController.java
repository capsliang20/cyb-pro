package com.qwwaq.cyb.console.controller;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.ReturnType;
import com.qwwaq.cyb.service.api.ArticleService;
import com.qwwaq.cyb.service.api.FollowerService;
import com.qwwaq.cyb.service.api.ProjectService;
import com.qwwaq.cyb.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "homepage")
public class HomepageController {
    @Resource
    FollowerService followerService;

    @Resource
    ProjectService projectService;

    @Resource
    ArticleService articleService;

    @Resource
    UserService userService;

    @RequestMapping(value = "follow", method = RequestMethod.GET)
    ReturnType follow(@Param("type")Integer type,@Param("targetId")Integer targetId,@Param("userId") Integer userId) {
        log.info("follow : type:{} , targetId:{}, userId:{}", type,targetId,userId);
        followerService.follow(type, targetId, userId);
        return ReturnType.ok("关注成功");
    }

    @RequestMapping(value = "unFollow", method = RequestMethod.GET)
    ReturnType unFollow(@Param("type")Integer type,@Param("targetId")Integer targetId,@Param("userId") Integer userId) {
        log.info("follow : type:{} , targetId:{}, userId:{}", type,targetId,userId);
        followerService.unfollow(type, targetId, userId);
        return ReturnType.ok("取消关注成功");
    }

    @RequestMapping(value = "listCreatedProject", method = RequestMethod.GET)
    ReturnType listCreatedProject(@Param("userId") Integer userId) {
        log.info("listCreatedProject userId:{}", userId);
        Map data=new HashMap();
        data.put("projectList", projectService.listProjectsWithCreator(userId));
        return ReturnType.ok("搜索成功",data);
    }

    @RequestMapping(value = "listFollowedProject", method = RequestMethod.GET)
    ReturnType listFollowedProject(@Param("userId") Integer userId) {
        log.info("listFollowedProject userId:{}", userId);
        Map data=new HashMap();
        data.put("projectList", projectService.listProjectsWithFollower(userId));
        return ReturnType.ok("搜索成功",data);
    }

    @RequestMapping(value = "listCreatedArticle", method = RequestMethod.GET)
    ReturnType listCreatedArticle(@Param("userId") Integer userId) {
        log.info("listCreatedArticle userId:{}", userId);
        Map data=new HashMap();
        data.put("articleList", articleService.listArticlesWithCreator(userId));
        return ReturnType.ok("搜索成功",data);
    }

    @RequestMapping(value = "listFollowedArticle", method = RequestMethod.GET)
    ReturnType listFollowedArticle(@Param("userId") Integer userId) {
        log.info("listFollowedArticle userId:{}", userId);
        Map data=new HashMap();
        data.put("articleList", articleService.listArticlesWithFollower(userId));
        return ReturnType.ok("搜索成功",data);
    }

    @RequestMapping(value = "listFollowers", method = RequestMethod.GET)
    ReturnType listFollowers(@Param("userId") Integer userId) {
        log.info("listFollowers userId:{}", userId);
        Map data=new HashMap();
        data.put("userList", userService.listFollowersWithUser(userId));
        return ReturnType.ok("搜索成功",data);
    }

    @RequestMapping(value = "listFollowedUsers", method = RequestMethod.GET)
    ReturnType listFollowedUsers(@Param("userId") Integer userId) {
        log.info("listFollowedUsers userId:{}", userId);
        Map data=new HashMap();
        data.put("userList", userService.listUsersWithFollower(userId));
        return ReturnType.ok("搜索成功",data);
    }


}
