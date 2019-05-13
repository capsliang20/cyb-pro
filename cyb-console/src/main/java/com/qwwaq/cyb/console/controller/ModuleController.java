package com.qwwaq.cyb.console.controller;

import com.qwwaq.cyb.entity.ReturnType;
import com.qwwaq.cyb.service.api.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "module")
public class ModuleController {

    @Resource
    ProjectService projectService;

    @RequestMapping(value = "moduleList", method = RequestMethod.GET)
    ReturnType moduleList() {
        log.info("invoke moduleList");
        Map data =new HashMap();
        data.put("moduleList", projectService.listModules());
        return ReturnType.ok("查询成功",data);

    }
}
