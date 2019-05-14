package com.qwwaq.cyb.service.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FollowerMapper {

    @Insert("insert into follower (type,target_id,user_id) values (#{type},#{targetId},#{userId})")
    Integer follow(@Param("type")Integer type,@Param("targetId") Integer targetId,@Param("userId") Integer userId);


    @Delete("delete from follower where type=#{type} and target_id=#{targetId} and user_id=#{userId}")
    Integer unFollow(@Param("type")Integer type,@Param("targetId") Integer targetId,@Param("userId") Integer userId);

    @Select("select count(id) from follower where type=#{type} and target_id=#{targetId}")
    @ResultType(Integer.class)
    Integer countFollowers(@Param("type")Integer type,@Param("targetId") Integer targetId);

    @Select("select count(id) from follower where type=#{type} and user_id=#{userId}")
    @ResultType(Integer.class)
    Integer countConcernedNum(@Param("type")Integer type,@Param("userId") Integer userId);


    @Select("select id from follower where type=#{type} and target_id=#{targetId} and user_id=#{userId}")
    @ResultType(Integer.class)
    Integer isFollowed(@Param("type")Integer type,@Param("targetId") Integer targetId,@Param("userId") Integer userId);

    @Select("select user_id from follower where type=#{type} and target_id=#{targetId}")
    @ResultType(Integer.class)
    List<Integer> listFollowers(@Param("type")Integer type, @Param("targetId") Integer targetId);

    @Select("select target_id from follower where type=#{type} and user_id=#{userId}")
    @ResultType(Integer.class)
    List<Integer> listTargets(@Param("type")Integer type, @Param("userId") Integer userId);


}
