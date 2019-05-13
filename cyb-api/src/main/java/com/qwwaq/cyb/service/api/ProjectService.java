package com.qwwaq.cyb.service.api;

import com.qwwaq.cyb.entity.Project;

import java.util.List;

public interface ProjectService {
    Integer createProject(Project project);
    Integer update(Project project);
    List<Project> queryProjectWithModule(String module);
    List<Project> listProjectsWithCreator(Integer userId);  //查看我的项目
    List<Project> listProjectsWithFollower(Integer userId); //查看我关注的项目
    Project queryProjectDetail(Integer id);
    Integer removeProject(Integer id);
    List<String> listModules();
}
