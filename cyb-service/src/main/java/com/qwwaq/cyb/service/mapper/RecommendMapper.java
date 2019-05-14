package com.qwwaq.cyb.service.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RecommendMapper {

    @Select("select target_id from recommend where type=#{type} and user_id=#{userId}")
    @ResultType(Integer.class)
    List<Integer> queryRecommendList(@Param("type")Integer type,@Param("userId") Integer userId);

}
