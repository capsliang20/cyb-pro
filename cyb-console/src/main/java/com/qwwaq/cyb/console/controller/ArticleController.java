package com.qwwaq.cyb.console.controller;

import com.alibaba.fastjson.JSON;
import com.qwwaq.cyb.console.util.MessageUtil;
import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.ReturnType;
import com.qwwaq.cyb.entity.User;
import com.qwwaq.cyb.service.api.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "article")
public class ArticleController {

    @Resource
    ArticleService articleService;

    /**
     *     private String title;
     *     private String content;
     *     private Integer creatorId;
     *     private String module;
     */
    @RequestMapping(value = "createArticle", method = RequestMethod.POST)
    ReturnType createArticle(@RequestBody Article article) {
        article.setCreateDate(new Date());
        log.info("article:{}", JSON.toJSONString(article));
        Map data = new HashMap();
        articleService.createArticle(article);
        return ReturnType.ok("创建成功");
    }

    /**
     *     private String id;
     *     private String title;
     *     private String content;
     *     private String module;
     */
    @RequestMapping(value = "updateArticle", method = RequestMethod.POST)
    ReturnType updateArticle(@RequestBody Article article) {
        log.info("article:{}", JSON.toJSONString(article));
        Map data = new HashMap();
        articleService.update(article);
        return ReturnType.ok("更新成功");
    }

    @RequestMapping(value = "queryArticleWithModule", method = RequestMethod.GET)
    ReturnType queryArticleWithModule(@Param("module")String module) {
        log.info("module:{}", module);
        Map data = new HashMap();
        List<Article> articleList = articleService.queryArticleWithModule(module);
        data.put("articleList", articleList);
        return ReturnType.ok("搜索成功", data);
    }

    @RequestMapping(value = "queryArticleDetail", method = RequestMethod.GET)
    ReturnType queryArticleDetail(@Param("id")Integer id) {
        log.info("queryArticleWithModule : article id:{}", id);
        Map data = new HashMap();
        data.put("project", articleService.queryArticleDetail(id));
        return ReturnType.ok("搜索成功", data);
    }

    @RequestMapping(value = "removeArticle", method = RequestMethod.GET)
    ReturnType removeArticle(@Param("id")Integer id) {
        log.info("id:{}", id);
       articleService.removeArticle(id);
       return ReturnType.ok("删除成功");

    }

}
