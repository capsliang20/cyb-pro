package com.qwwaq.cyb.service.mapper;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface ProjectMapper {

    @Insert("insert into project (name,introduction,image_address,create_date,creator,module) values (#{name},#{introduction}," +
            "#{imageAddress},#{createDate},#{creatorId},#{module})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer insertProject(Project project);

    @Select("select id,name,introduction,image_address,create_date,creator,module from project where id =#{id}")
    @Results(id = "projectMap",value = {
            @Result(property = "id",column = "id",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "name",column = "name",jdbcType = JdbcType.VARCHAR,javaType = String.class),
            @Result(property = "introduction",column = "introduction",jdbcType = JdbcType.VARCHAR,javaType = String.class),
            @Result(property = "imageAddress",column = "image_address",jdbcType = JdbcType.VARCHAR,javaType = String.class),
            @Result(property = "createDate",column = "create_date",jdbcType = JdbcType.DATE,javaType = Date.class),
            @Result(property = "creatorId",column = "creator",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "creatorName",column = "creator",one=@One(select = "com.qwwaq.cyb.service.mapper.UserMapper.queryNameById")),
            @Result(property = "module",column = "module",jdbcType = JdbcType.VARBINARY,javaType = String.class)
    })
    Project queryProject(@Param("id") Integer id);

    @Select("select id,name,introduction,image_address,module from project where  module=#{module}")
    @Results(id = "simpleProjectMap",value = {
            @Result(property = "id",column = "id",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "name",column = "name",jdbcType = JdbcType.VARCHAR,javaType = String.class),
            @Result(property = "introduction",column = "introduction",jdbcType = JdbcType.VARCHAR,javaType = String.class),
            @Result(property = "imageAddress",column = "image_address",jdbcType = JdbcType.VARCHAR,javaType = String.class),
           @Result(property = "module",column = "module",jdbcType = JdbcType.VARBINARY,javaType = String.class)
    })
    List<Project> queryProjectWithModule(@Param("module")String module);

    @Select("select id,name,introduction,image_address,module from project where  creator=#{userId}")
    @ResultMap("simpleProjectMap")
    List<Project> queryProjectListWithCreator(@Param("userId")Integer userId);

    @Select("select id,name,introduction,image_address,module from project where id=#{projectId}")
    @ResultMap("simpleProjectMap")
    Project querySimpleProjectWithId(@Param("projectId")Integer projectId);


    @Select("select name from module")
    @ResultType(String.class)
    List<String> listModules();

    @UpdateProvider(type = MapperGenerator.class,method = "updateProjectString")
    @ResultType(Integer.class)
    Integer updateProject(Project project);


    @Delete("delete from project where id =#{id}")
    @ResultType(Integer.class)
    Integer removeProject(@Param("id") Integer id);


}
