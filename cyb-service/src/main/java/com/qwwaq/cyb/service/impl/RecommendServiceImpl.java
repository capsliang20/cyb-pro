package com.qwwaq.cyb.service.impl;

import com.qwwaq.cyb.entity.*;
import com.qwwaq.cyb.service.api.RecommendService;
import com.qwwaq.cyb.service.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@Slf4j
public class RecommendServiceImpl implements RecommendService {

    @Resource
    RecommendMapper recommendMapper;

    @Resource
    ProjectMapper projectMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    FollowerMapper followerMapper;

    @Resource
    ArticleMapper articleMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public List recommend(Integer type, Integer userId) {
        log.info("recommend type:{}, userId:{}", type,userId);
        List<Integer> targetList = recommendMapper.queryRecommendList(type, userId);

        if(type==Recommend.PROJECT_RECOMMEND){
            List<Project> projectList=new ArrayList<>(targetList.size());
            targetList.forEach(targetId-> projectList.add(projectMapper.querySimpleProjectWithId(targetId)));
            projectList.forEach(project -> {
                project.setCommentNum(commentMapper.countComment(Comment.PROJECT_COMMENT, project.getId()));
                project.setFollowerNum(followerMapper.countFollowers(Follower.PROJECT_FOLLOWER, project.getId()));
                project.setIsFollowed(followerMapper.isFollowed(Follower.PROJECT_FOLLOWER, project.getId(), userId)==null? Boolean.FALSE:Boolean.TRUE);
            });
            return projectList;

        }else if (type==Recommend.ARTICLE_RECOMMEND){
            List<Article> articleList=new ArrayList<>(targetList.size());
            targetList.forEach(targetId->articleList.add(articleMapper.querySimpleArticleWithId(targetId) ));
            articleList.forEach(article -> {
                article.setFollowerNum(followerMapper.countFollowers(Follower.ARTICLE_FOLLOWER, article.getId()));
                article.setCommentNum(commentMapper.countComment(Comment.ARTICLE_COMMENT, article.getId()));
                article.setIsFollowed(followerMapper.isFollowed(Follower.ARTICLE_FOLLOWER, article.getId(), userId)==null?Boolean.FALSE:Boolean.TRUE);

            });
            return articleList;

        }else if(type==Recommend.USER_RECOMMEND){
            List<User> userList=new ArrayList<>(targetList.size());
            targetList.forEach(targetId->userList.add(userMapper.querySimpleUserInfoWithId(targetId)));
            userList.forEach(user -> {
                user.setFollowerNum(followerMapper.countFollowers(Follower.USER_FOLLOWER, user.getId()));
                user.setConcernedNum(followerMapper.countConcernedNum(Follower.USER_FOLLOWER, user.getId()));
                user.setIsFollowed(followerMapper.isFollowed(Follower.USER_FOLLOWER, user.getId(), userId)==null?Boolean.FALSE:Boolean.TRUE);
            });
            return userList;
        }
        return new ArrayList();
    }
}
