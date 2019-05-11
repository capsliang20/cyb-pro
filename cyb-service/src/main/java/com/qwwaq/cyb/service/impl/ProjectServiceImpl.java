package com.qwwaq.cyb.service.impl;

import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.service.api.ProjectService;
import com.qwwaq.cyb.service.mapper.ProjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {
    @Resource
    ProjectMapper projectMapper;
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
        return projectMapper.queryArticleWithModule(module);
    }

    @Override
    public Integer removeProject(Integer id) {
        return projectMapper.removeProject(id);
    }
}
