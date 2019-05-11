package com.qwwaq.cyb.console.controller;

import com.alibaba.fastjson.JSON;
import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.ReturnType;
import com.qwwaq.cyb.service.api.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "project")
public class ProjectController {
    @Resource
    ProjectService projectService;

    /**
     private String name;
     private String introduction;
     private String detail;
     private String imageAddress;
     private Integer creatorId;
     private String module;
     */
    @RequestMapping(value = "createProject", method = RequestMethod.POST)
    ReturnType register(@RequestBody Project project) {
        project.setCreateDate(new Date());
        log.info("project:{}", JSON.toJSONString(project));
        projectService.createProject(project);
        return ReturnType.ok("创建成功");
    }


    /**
     private String id;
     private String name;
     private String introduction;
     private String detail;
     private String imageAddress;
     private String module;
     */
    @RequestMapping(value = "updateProject", method = RequestMethod.POST)
    ReturnType updateProject(@RequestBody Project project) {
        log.info("project:{}", JSON.toJSONString(project));
        projectService.update(project);
        return ReturnType.ok("更新成功");
    }

    @RequestMapping(value = "queryProjectWithModule", method = RequestMethod.GET)
    ReturnType queryProjectWithModule(@Param("module")String module) {
        log.info("module:{}", module);
        Map data = new HashMap();
        List<Project> projectList = projectService.queryProjectWithModule(module);
        data.put("projectList", projectList);
        return ReturnType.ok("搜索成功", data);
    }

    @RequestMapping(value = "removeProject", method = RequestMethod.GET)
    ReturnType removeProject(@Param("id")Integer id) {
        log.info("id:{}", id);
        projectService.removeProject(id);
        return ReturnType.ok("删除成功");

    }
}
