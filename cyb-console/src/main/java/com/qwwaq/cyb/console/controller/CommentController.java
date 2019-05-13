package com.qwwaq.cyb.console.controller;


import com.alibaba.fastjson.JSON;
import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Comment;
import com.qwwaq.cyb.entity.ReturnType;
import com.qwwaq.cyb.service.api.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "comment")
public class CommentController {

    @Resource
    CommentService commentService;

    /**
     String content;
     Integer type;
     Integer targetId;
     Integer userId;
     */
    @RequestMapping(value = "addComment", method = RequestMethod.POST)
    ReturnType addComment(@RequestBody Comment comment) {
        log.info("addComment:{}", JSON.toJSONString(comment));
        commentService.addComment(comment);
        return ReturnType.ok("评论成功");
    }

//    @RequestMapping(value = "queryCommentList", method = RequestMethod.GET)
//    ReturnType queryCommentList(@Param("type")Integer type,@Param("targetId") Integer targetId) {
//        log.info("queryCommentList type:{}, targetId:{} ", type,targetId);
//        List<Comment> commentList = commentService.queryCommentList(type, targetId);
//        Map data=new HashMap();
//        data.put("commentList", commentList);
//        return ReturnType.ok("查询成功",data);
//    }


    @RequestMapping(value = "removeComment", method = RequestMethod.GET)
    ReturnType removeComment(@Param("type")Integer type,@Param("targetId") Integer targetId) {
        log.info("removeComment type:{}, targetId:{} ", type,targetId);
        //commentService.removeComment(type, targetId);
        return ReturnType.ok("删除成功");

    }


}
