package com.qwwaq.cyb.service.impl;

import com.qwwaq.cyb.service.api.FollowerService;
import com.qwwaq.cyb.service.mapper.FollowerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
@Slf4j
public class FollowerServiceImpl implements FollowerService {

    @Resource
    FollowerMapper followerMapper;

    @Override
    public Integer follow(Integer type, Integer targetId, Integer userId) {
        Integer followerId=followerMapper.isFollowed(type, targetId, userId);
        log.info("followerId is existed?:{}", followerId);
        if(followerId==null||followerId==0) {
            return followerMapper.follow(type, targetId, userId);
        }
        else{
            return followerId;
        }
    }

    @Override
    public Integer unfollow(Integer type, Integer targetId, Integer userId) {
        return followerMapper.unFollow(type, targetId, userId);
    }
}
