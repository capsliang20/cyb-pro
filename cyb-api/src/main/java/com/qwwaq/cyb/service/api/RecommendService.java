package com.qwwaq.cyb.service.api;

import java.util.List;

public interface RecommendService {
    List recommend(Integer type, Integer userId);
}
