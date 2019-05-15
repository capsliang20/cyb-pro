package com.qwwaq.cyb.service.mapper;

import com.qwwaq.cyb.entity.Article;
import com.qwwaq.cyb.entity.Project;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleMapper {

    @Insert("insert into article (title,content,create_date,creator,module) values (#{title},#{content},#{createDate}," +
            "#{creatorId},#{module})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer insertArticle(Article article);

    @Select("select id,title,content,create_date,creator,module from article where id =#{id}")
    @Results(id = "articleMap",value = {
            @Result(property = "id",column = "id",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "title",column = "title",jdbcType = JdbcType.VARCHAR,javaType = String.class),
            @Result(property = "content",column = "content",jdbcType = JdbcType.VARCHAR,javaType = String.class),
            @Result(property = "createDate",column = "create_date",jdbcType = JdbcType.DATE,javaType = Date.class),
            @Result(property = "creatorId",column = "creator",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "module",column = "module",jdbcType = JdbcType.VARBINARY,javaType = String.class)
    })
    Article queryArticle(@Param("id") Integer id);

    @Select("select id,title,content,create_date,creator,module from article where module=#{module}")
    @ResultMap("articleMap")
    List<Article> queryArticleWithModule(@Param("module")String module);

    @Select("select id,title,content,create_date,creator,module from article where creator=#{userId}")
    @ResultMap("articleMap")
    List<Article> queryArticleListWithCreator(@Param("userId")Integer userId);

    @Select("select id,title,content,create_date,creator,module from article where id=#{articleId}")
    @ResultMap("articleMap")
    Article querySimpleArticleWithId(@Param("articleId")Integer articleId);


    @UpdateProvider(type = MapperGenerator.class,method = "updateArticleString")
    @ResultType(Integer.class)
    Integer updateArticle(Article article);

    @Delete("delete from article where id =#{id}")
    @ResultType(Integer.class)
    Integer removeArticle(@Param("id") Integer id);
}
