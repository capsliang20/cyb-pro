package com.qwwaq.cyb.service.api;


import com.qwwaq.cyb.entity.User;

import java.util.List;

public interface UserService {
    Integer insertUser(User user);
    User userLogin(String account,String password);
    User queryUser(Integer id);
    Integer update(User user);
    Integer updatePassword(Integer id,String password);
    Integer removeUser(Integer id);

    List<User> listUsersWithFollower(Integer userId);  //查看我关注的人
    List<User> listFollowersWithUser(Integer userId); //查看关注我的人
}

