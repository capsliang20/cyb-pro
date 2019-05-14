package com.qwwaq.cyb.service.mapper;

import com.qwwaq.cyb.entity.Comment;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment (content,type,target_id,user_id) values (#{content},#{type},#{targetId}," +
            "#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    Integer insertComment(Comment comment);

    @Select("select id,content,type,target_id,user_id from comment where id =#{id}")
    @Results(id = "commentMap",value = {
            @Result(property = "id",column = "id",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "content",column = "content",jdbcType = JdbcType.VARCHAR,javaType = String.class),
            @Result(property = "type",column = "type",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "targetId",column = "target_id",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "userId",column = "user_id",jdbcType = JdbcType.INTEGER,javaType = Integer.class),
            @Result(property = "userName",column = "user_id",one=@One(select = "com.qwwaq.cyb.service.mapper.UserMapper.queryNameById")),
            @Result(property = "userImage",column = "user_id",one=@One(select = "com.qwwaq.cyb.service.mapper.UserMapper.queryImageById")),

    })
    Comment queryComment(@Param("id") Integer id);

    @Select("select id,content,type,target_id,user_id from comment where type=#{type} and target_id =#{targetId}")
    @ResultMap("commentMap")
    List<Comment> queryCommentWithTarget(@Param("type") Integer type,@Param("targetId")Integer targetId);

    @Select("select id from comment where type=#{type} and target_id=#{targetId}")
    @ResultType(Integer.class)
    List<Integer> queryReplyIdList(@Param("type")Integer type,@Param("targetId") Integer targetId);

    @Select("select count(id) from comment where type=#{type} and target_id=#{targetId}")
    @ResultType(Integer.class)
    Integer countComment(@Param("type")Integer type,@Param("targetId")Integer targetId);


    @Delete("delete from comment where id =#{id}")
    @ResultType(Integer.class)
    Integer removeSingleComment(@Param("id") Integer id);

    @Delete("delete from comment where type=#{type} and target_id=#{targetId}")
    @ResultType(Integer.class)
    Integer removeCommentWithTarget(@Param("type")Integer type,@Param("targetId") Integer targetId);
}
