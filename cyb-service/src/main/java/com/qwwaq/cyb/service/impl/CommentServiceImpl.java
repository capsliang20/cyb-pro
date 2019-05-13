package com.qwwaq.cyb.service.impl;

import com.qwwaq.cyb.entity.Comment;
import com.qwwaq.cyb.service.api.CommentService;
import com.qwwaq.cyb.service.mapper.CommentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Resource
    CommentMapper commentMapper;

    @Override
    public Integer addComment(Comment comment) {
        return commentMapper.insertComment(comment);
    }

    @Override
    public List<Comment> queryCommentList(Integer type, Integer targetId) {
        List<Comment> commentList= commentMapper.queryCommentWithTarget(type, targetId);
        commentList.forEach(comment ->queryReplyList(comment));
        return commentList;
    }

    private void queryReplyList(Comment comment){
        List<Comment> replyList = commentMapper.queryCommentWithTarget(Comment.COMMENT_REPLY, comment.getId());
        comment.setCommentList(replyList);
        if(!comment.getCommentList().isEmpty()){
            comment.getCommentList().forEach(replyComment->queryReplyList(replyComment));
        }
    }

    @Override
    public Integer removeMultiComment(Integer type, Integer targetId) {
        log.info("removeMultiComment :{} ,{}", type,targetId);
        List<Integer> removeList = commentMapper.queryReplyIdList(type, targetId);
        log.info("removeList=:{}", removeList.toString());
        removeList.forEach(replyId->removeReplyList(replyId));
        return commentMapper.removeCommentWithTarget(type, targetId);
    }

    @Override
    public Integer removeSingleComment(Integer id){
        log.info("removeSingleComment:{} " ,id );
        List<Integer> removeList = commentMapper.queryReplyIdList(Comment.COMMENT_REPLY , id);
        log.info("removeList=:{}", removeList.toString());
        removeList.forEach(replyId->removeReplyList(replyId));
        return commentMapper.removeSingleComment(id);
    }

    private void removeReplyList(Integer id){
        commentMapper.removeSingleComment(id);
        List<Integer> replyList = commentMapper.queryReplyIdList(Comment.COMMENT_REPLY, id);
        log.info("removeReplyList replyList:{}", replyList.toString());
        if(!replyList.isEmpty()){
            replyList.forEach(commentId-> removeReplyList(commentId));
        }
    }

}
