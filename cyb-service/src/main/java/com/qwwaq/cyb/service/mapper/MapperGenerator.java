package com.qwwaq.cyb.service.mapper;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class MapperGenerator {
    public String updateProjectString(Project project){
        StringBuilder stringBuilder=new StringBuilder("update project set id=").append(project.getId());
        if (project.getName()!=null&&!project.getName().equals(""))
            stringBuilder.append(" , name='").append(project.getName()).append("' ");
        if(project.getIntroduction()!=null)
            stringBuilder.append(" , introduction='").append(project.getIntroduction()).append("' ");
        if(project.getImageAddress()!=null)
            stringBuilder.append(" , image_address='").append(project.getImageAddress()).append("' ");
        if(project.getModule()!=null)
            stringBuilder.append(" , module='").append(project.getModule()).append("' ");
        stringBuilder.append(" where id =").append(project.getId());
        log.info("updateProjectString : {}", stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String updateArticleString(Article article){
        StringBuilder stringBuilder=new StringBuilder("update article set id=").append(article.getId());
        if(article.getTitle()!=null&&!article.getTitle().equals(""))
            stringBuilder.append(" , title='").append(article.getTitle()).append("' ");
        if(article.getContent()!=null)
            stringBuilder.append(" , content='").append(article.getContent()).append("' ");
        if(article.getModule()!=null)
            stringBuilder.append(" , module='").append(article.getModule()).append("' ");
        stringBuilder.append(" where id =").append(article.getId());
        log.info("updateArticleString : {}", stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String updateUserString(User user){
        StringBuilder stringBuilder=new StringBuilder("update user set id=").append(user.getId());
        if(user.getName()!=null&&!user.getName().equals(""))
            stringBuilder.append(" , name='").append(user.getName()).append("' ");
        if(user.getPassword()!=null)
            stringBuilder.append(" , password='").append(user.getPassword()).append("' ");
        if(user.getIntroduction()!=null)
            stringBuilder.append(" , introduction='").append(user.getIntroduction()).append("' ");
        if(user.getImageAddress()!=null)
            stringBuilder.append(" , image_address='").append(user.getImageAddress()).append("' ");
        stringBuilder.append(" where id =").append(user.getId());
        log.info("updateUserString : {}", stringBuilder.toString());
        return stringBuilder.toString();
    }
}
