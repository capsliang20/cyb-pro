package com.qwwaq.cyb.service.impl;

import com.qwwaq.cyb.entity.Comment;
import com.qwwaq.cyb.entity.Follower;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.User;
import com.qwwaq.cyb.service.api.CommentService;
import com.qwwaq.cyb.service.api.ProjectService;
import com.qwwaq.cyb.service.mapper.CommentMapper;
import com.qwwaq.cyb.service.mapper.FollowerMapper;
import com.qwwaq.cyb.service.mapper.ProjectMapper;
import com.qwwaq.cyb.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class ProjectServiceImpl implements ProjectService {
    @Resource
    ProjectMapper projectMapper;
    @Resource
    CommentService commentService;

    @Resource
    CommentMapper commentMapper;

    @Resource
    FollowerMapper followerMapper;

    @Resource
    UserMapper userMapper;

    @Override
    public Integer createProject(Project project) {
        return projectMapper.insertProject(project);
    }

    @Override
    public Integer update(Project project) {
        return projectMapper.updateProject(project);
    }

    @Override
    public List<Project> queryProjectWithModule(String module,Integer userId) {
        List<Project> projectList = projectMapper.queryProjectWithModule(module);
        projectList.forEach(project -> {
            User creator =userMapper.querySimpleUserInfo(project.getCreatorId());
            creator.setIsFollowed(followerMapper.isFollowed(Follower.USER_FOLLOWER, creator.getId(), userId)==null?Boolean.FALSE:Boolean.TRUE);
            project.setCreator(creator);
            project.setCommentNum(commentMapper.countComment(Comment.PROJECT_COMMENT, project.getId()));
            project.setFollowerNum(followerMapper.countFollowers(Follower.PROJECT_FOLLOWER, project.getId()));
            project.setIsFollowed(followerMapper.isFollowed(Follower.PROJECT_FOLLOWER, project.getId(), userId)==null? Boolean.FALSE:Boolean.TRUE);
        });
        return projectList;
    }

    @Override
    public List<Project> listProjectsWithCreator(Integer userId) {
        List<Project> projectList = projectMapper.queryProjectListWithCreator(userId);
        projectList.forEach(project -> {
            User creator =userMapper.querySimpleUserInfo(project.getCreatorId());
            creator.setIsFollowed(followerMapper.isFollowed(Follower.USER_FOLLOWER, creator.getId(), userId)==null?Boolean.FALSE:Boolean.TRUE);
            project.setCreator(creator);
            project.setCommentNum(commentMapper.countComment(Comment.PROJECT_COMMENT, project.getId()));
            project.setFollowerNum(followerMapper.countFollowers(Follower.PROJECT_FOLLOWER, project.getId()));
        });
        return projectList;
    }

    @Override
    public List<Project> listProjectsWithFollower(Integer userId) {
        List<Integer> targetList = followerMapper.listTargets(Follower.PROJECT_FOLLOWER, userId);
        log.info("listProjectWithFollower targetList:{}", targetList.toString());
        List<Project> projectList=new ArrayList<>();
        targetList.forEach(targetId->{
            projectList.add(projectMapper.querySimpleProjectWithId(targetId));
        });
        projectList.forEach(project -> {
            User creator =userMapper.querySimpleUserInfo(project.getCreatorId());
            creator.setIsFollowed(followerMapper.isFollowed(Follower.USER_FOLLOWER, creator.getId(), userId)==null?Boolean.FALSE:Boolean.TRUE);
            project.setCreator(creator);
            project.setCommentNum(commentMapper.countComment(Comment.PROJECT_COMMENT, project.getId()));
            project.setFollowerNum(followerMapper.countFollowers(Follower.PROJECT_FOLLOWER, project.getId()));
            project.setIsFollowed(Boolean.TRUE);
        });
        return projectList;
    }


    @Override
    public Project queryProjectDetail(Integer projectId,Integer userId) {
        Project project = projectMapper.queryProject(userId);
        List<Comment> commentList = commentService.queryCommentList(Comment.PROJECT_COMMENT, project.getId());
        project.setCommentList(commentList);
        User creator =userMapper.querySimpleUserInfo(project.getCreatorId());
        creator.setIsFollowed(followerMapper.isFollowed(Follower.USER_FOLLOWER, creator.getId(), userId)==null?Boolean.FALSE:Boolean.TRUE);
        project.setCreator(creator);
        project.setFollowerNum(followerMapper.countFollowers(Follower.PROJECT_FOLLOWER, project.getId()));
        project.setCommentNum(commentMapper.countComment(Comment.PROJECT_COMMENT, project.getId()));
        project.setIsFollowed(followerMapper.isFollowed(Follower.PROJECT_FOLLOWER, project.getId(), userId)==null? Boolean.FALSE:Boolean.TRUE);
        return project;
    }

    @Override
    public Integer removeProject(Integer id) {
        commentService.removeMultiComment(Comment.PROJECT_COMMENT, id);
        return projectMapper.removeProject(id);
    }

    @Override
    public List<String> listModules() {
        return projectMapper.listModules();
    }
}
