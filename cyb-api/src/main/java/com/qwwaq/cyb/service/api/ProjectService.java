package com.qwwaq.cyb.service.api;

import com.qwwaq.cyb.entity.Project;

import java.util.List;

public interface ProjectService {
    Integer createProject(Project project);
    Integer update(Project project);
    List<Project> queryProjectWithModule(String module);
    Integer removeProject(Integer id);
}
