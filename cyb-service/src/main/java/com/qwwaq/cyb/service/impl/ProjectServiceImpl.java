package com.qwwaq.cyb.service.impl;

import com.qwwaq.cyb.entity.Comment;
import com.qwwaq.cyb.entity.Follower;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.service.api.CommentService;
import com.qwwaq.cyb.service.api.ProjectService;
import com.qwwaq.cyb.service.mapper.CommentMapper;
import com.qwwaq.cyb.service.mapper.FollowerMapper;
import com.qwwaq.cyb.service.mapper.ProjectMapper;
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

    @Override
    public Integer createProject(Project project) {
        return projectMapper.insertProject(project);
    }

    @Override
    public Integer update(Project project) {
        return projectMapper.updateProject(project);
    }

    @Override
    public List<Project> queryProjectWithModule(String module) {
        List<Project> projectList = projectMapper.queryProjectWithModule(module);
        projectList.forEach(project -> {
            project.setCommentNum(commentMapper.countComment(Comment.PROJECT_COMMENT, project.getId()));
            project.setFollowerNum(followerMapper.countFollowers(Follower.PROJECT_FOLLOWER, project.getId()));
        });
        return projectList;
    }

    @Override
    public List<Project> listProjectsWithCreator(Integer userId) {
        List<Project> projectList = projectMapper.queryProjectListWithCreator(userId);
        projectList.forEach(project -> {
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
            project.setCommentNum(commentMapper.countComment(Comment.PROJECT_COMMENT, project.getId()));
            project.setFollowerNum(followerMapper.countFollowers(Follower.PROJECT_FOLLOWER, project.getId()));
        });
        return projectList;
    }


    @Override
    public Project queryProjectDetail(Integer id) {
        Project project = projectMapper.queryProject(id);
        List<Comment> commentList = commentService.queryCommentList(Comment.PROJECT_COMMENT, project.getId());
        project.setCommentList(commentList);
//        project.setCommentNum(commentList.size());
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
