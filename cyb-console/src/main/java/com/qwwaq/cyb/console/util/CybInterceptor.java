package com.qwwaq.cyb.console.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CybInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        log.info("{} get into the system.",session.getId());

        String account = redisTemplate.opsForValue().get(session.getId());

        if(account==null||account.equals("")) {
            log.info("account is null, please log in or register! ");
            redisTemplate.opsForValue().set(session.getId(), "unknown", MessageUtil.CACHE_TIME, TimeUnit.MINUTES);
        }
        else {
            log.info("account: {} active",account);
            redisTemplate.opsForValue().set(session.getId(), account, MessageUtil.CACHE_TIME, TimeUnit.MINUTES);
        }

//        String account = redisTemplate.opsForValue().get(session.getId());
//
//        if(account==null||account.equals("")) {         //未登录状态
//            String path=request.getServletPath();
//            if(path.equals("/")) {   //访问主页,直接放行
//                log.info("{} is visiting the index",session.getId());
//                return true;
//            }else if(Pattern.matches("/user/.*", path)){ //调用登录注册相关接口，放行
//                log.info("{} is visiting {}",session.getId(),path );
//                return true;
//            }
//            log.info("account is null, please log in or register! ");   //未登录态访问其他接口，转向主页
//            response.sendRedirect("/cyb_app");
//            //redisTemplate.opsForValue().set(session.getId(), "unknown", MessageUtil.CACHE_TIME, TimeUnit.MINUTES);
//        }
//        else {
//            log.info("account: {} active",account);
//            redisTemplate.opsForValue().set(session.getId(), account, MessageUtil.CACHE_TIME, TimeUnit.MINUTES);
//        }


//        System.out.println(request.getContextPath());
//        System.out.println(request.getServletPath());
//

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //System.out.println("Interceptor stop~~~~~~~~~~~`");
    }
}
