package com.qwwaq.cyb.service.api;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Comment;

import java.util.List;

public interface CommentService {
    Integer addComment(Comment comment);
    List<Comment> queryCommentList(Integer type,Integer targetId);
    Integer removeMultiComment(Integer type,Integer targetId);
    Integer removeSingleComment(Integer commentId);
}
