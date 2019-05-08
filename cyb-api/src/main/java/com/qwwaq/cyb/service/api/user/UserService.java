package com.qwwaq.cyb.service.api.user;


import com.qwwaq.cyb.entity.user.User;

public interface UserService {
    Integer insertUser(User user);
    User userLogin(String account,String password);
    User queryUser(Integer id);
    Integer update(User user);
    Integer updatePassword(Integer id,String password);
    Integer removeUser(Integer id);
}

