package com.qwwaq.cyb.service.impl;


import com.qwwaq.cyb.entity.Comment;
import com.qwwaq.cyb.entity.Follower;
import com.qwwaq.cyb.entity.Project;
import com.qwwaq.cyb.entity.User;
import com.qwwaq.cyb.service.api.UserService;
import com.qwwaq.cyb.service.mapper.FollowerMapper;
import com.qwwaq.cyb.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    FollowerMapper followerMapper;

    @Override
    public Integer insertUser(User user) {
        Integer verifyId=userMapper.queryIdByAccount(user.getAccount());
        if(verifyId==null||verifyId==0) {
            userMapper.insertUser(user);
            return user.getId();
        }
        else
            return null;
    }

    @Override
    public User userLogin(String account, String password) {
        if(password==null||password.equals("")){            //通过验证码登录且已验证
            User user= userMapper.queryUserByAccount(account);
            user.setFollowerNum(followerMapper.countFollowers(Follower.USER_FOLLOWER, user.getId()));
            user.setConcernedNum(followerMapper.countConcernedNum(Follower.USER_FOLLOWER, user.getId()));
            return user;
        }
        else{
            String verifyPassword=userMapper.queryPasswordByAccount(account);
            if (password.equals(verifyPassword)) {
                User user = userMapper.queryUserByAccount(account);
                user.setFollowerNum(followerMapper.countFollowers(Follower.USER_FOLLOWER, user.getId()));
                user.setConcernedNum(followerMapper.countConcernedNum(Follower.USER_FOLLOWER, user.getId()));
                return user;
            }
            else
                return null;
        }
    }

    @Override
    public User queryUser(Integer id) {
        return userMapper.queryUser(id);
    }

    @Override
    public Integer update(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public Integer updatePassword(Integer id, String password) {
        return userMapper.updatePassword(id, password);
    }

    @Override
    public Integer removeUser(Integer id) {
        return userMapper.removeUser(id);
    }

    @Override
    public List<User> listUsersWithFollower(Integer userId) {
        List<Integer> targetList = followerMapper.listTargets(Follower.USER_FOLLOWER, userId);
        log.info("listUsersWithFollower targetList:{}", targetList.toString());
        List<User> userList=new ArrayList<>();
        targetList.forEach(targetId->{
            userList.add(userMapper.querySimpleUserInfoWithId(targetId));
        });
        userList.forEach(user -> {
            user.setFollowerNum(followerMapper.countFollowers(Follower.USER_FOLLOWER, user.getId()));
            user.setConcernedNum(followerMapper.countConcernedNum(Follower.USER_FOLLOWER, user.getId()));
            user.setIsFollowed(Boolean.TRUE);
        });
        return userList;
    }

    @Override
    public List<User> listFollowersWithUser(Integer userId) {
        List<Integer> targetList = followerMapper.listFollowers(Follower.USER_FOLLOWER, userId);
        log.info("listUsersWithFollower targetList:{}", targetList.toString());
        List<User> userList=new ArrayList<>();
        targetList.forEach(targetId->{
            userList.add(userMapper.querySimpleUserInfoWithId(targetId));
        });
        userList.forEach(user -> {
            user.setFollowerNum(followerMapper.countFollowers(Follower.USER_FOLLOWER, user.getId()));
            user.setConcernedNum(followerMapper.countConcernedNum(Follower.USER_FOLLOWER, user.getId()));
            user.setIsFollowed(followerMapper.isFollowed(Follower.USER_FOLLOWER, user.getId(), userId)==null?Boolean.FALSE:Boolean.TRUE);
        });
        return userList;
    }
}
