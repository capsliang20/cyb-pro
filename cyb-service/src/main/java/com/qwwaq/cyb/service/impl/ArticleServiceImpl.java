package com.qwwaq.cyb.service.impl;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Comment;
import com.qwwaq.cyb.entity.Follower;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.service.api.ArticleService;
import com.qwwaq.cyb.service.api.CommentService;
import com.qwwaq.cyb.service.mapper.ArticleMapper;
import com.qwwaq.cyb.service.mapper.CommentMapper;
import com.qwwaq.cyb.service.mapper.FollowerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Resource
    ArticleMapper articleMapper;

    @Resource
    CommentService commentService;

    @Resource
    CommentMapper commentMapper;

    @Resource
    FollowerMapper followerMapper;

    @Override
    public Integer createArticle(Article article) {
        return articleMapper.insertArticle(article);
    }

    @Override
    public Integer update(Article article) {
        return articleMapper.updateArticle(article);
    }



    @Override
    public List<Article> queryArticleWithModule(String module) {
        List<Article> articleList = articleMapper.queryArticleWithModule(module);
        articleList.forEach(article -> {
            article.setFollowerNum(followerMapper.countFollowers(Follower.ARTICLE_FOLLOWER, article.getId()));
            article.setCommentNum(commentMapper.countComment(Comment.ARTICLE_COMMENT, article.getId()));
        });
        return articleList;
    }

    @Override
    public List<Article> listArticlesWithCreator(Integer userId) {
        List<Article> articleList = articleMapper.queryArticleListWithCreator(userId);
        articleList.forEach(article -> {
            article.setFollowerNum(followerMapper.countFollowers(Follower.ARTICLE_FOLLOWER, article.getId()));
            article.setCommentNum(commentMapper.countComment(Comment.ARTICLE_COMMENT, article.getId()));
        });
        return articleList;
    }

    @Override
    public List<Article> listArticlesWithFollower(Integer userId) {
        List<Integer> targetList = followerMapper.listTargets(Follower.ARTICLE_FOLLOWER, userId);
        log.info("listArticleWithFollower targetList:{}", targetList.toString());
        List<Article> articleList=new ArrayList<>();
        targetList.forEach(targetId->{
            articleList.add(articleMapper.querySimpleArticleWithId(targetId));
        });
        articleList.forEach(article -> {
            article.setCommentNum(commentMapper.countComment(Comment.ARTICLE_COMMENT, article.getId()));
            article.setFollowerNum(followerMapper.countFollowers(Follower.ARTICLE_FOLLOWER, article.getId()));
        });
        return articleList;
    }


    @Override
    public Article queryArticleDetail(Integer id) {
        Article article = articleMapper.queryArticle(id);
        List<Comment> commentList = commentService.queryCommentList(Comment.ARTICLE_COMMENT, article.getId());
        article.setCommentList(commentList);
        article.setCommentNum(commentList.size());
        return article;
    }

    @Override
    public Integer removeArticle(Integer id) {
        commentService.removeMultiComment(Comment.ARTICLE_COMMENT, id);
        return articleMapper.removeArticle(id);
    }
}
