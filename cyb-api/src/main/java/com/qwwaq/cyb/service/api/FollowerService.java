package com.qwwaq.cyb.service.api;

import java.util.List;

public interface FollowerService {
    Integer follow(Integer type,Integer targetId,Integer userId);
    Integer unfollow(Integer type,Integer targetId,Integer userId);
}
