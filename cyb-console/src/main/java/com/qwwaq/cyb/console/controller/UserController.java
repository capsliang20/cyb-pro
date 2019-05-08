package com.qwwaq.cyb.console.controller;

import com.qwwaq.cyb.console.util.AccountType;
import com.qwwaq.cyb.console.util.MessageUtil;
import com.qwwaq.cyb.entity.ReturnType;
import com.qwwaq.cyb.entity.User;
import com.qwwaq.cyb.service.api.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Resource
    UserService userService;

    @Autowired
    JavaMailSender mailSender;


    /**
     * 注册， 注册成功自动登录
     *
     * @param name
     * @param account
     * @param password
     * @param code
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    ReturnType register(@Param("name") String name, @Param("account") String account, @Param("password") String password, @Param("code") String code, HttpServletRequest httpServletRequest) {
        log.info("controller register. name={}, account={}, password={}, code={}", name, account, password, code);
        Map data = new HashMap();
        String verifyCode = redisTemplate.opsForValue().get(account);
        if (code != null && code.equals(verifyCode)) {
            User user = new User(null, name, account, password,"",MessageUtil.NET_PATH+"default.png");
            Integer userId = userService.insertUser(user);
            if (userId != null && userId != 0) {
                redisTemplate.opsForValue().set(httpServletRequest.getSession().getId(), account, MessageUtil.CACHE_TIME, TimeUnit.MINUTES);
                data.put("userId", userId);
                data.put("account", account);
                data.put("name", name);
                return ReturnType.ok("success", data);
            } else
                return ReturnType.failure("account: " + account + " already exists!");
        } else
            return ReturnType.failure("验证失败");
    }

    @RequestMapping(value = "update",method = RequestMethod.GET)
    ReturnType update(@Param("id") Integer id,@Param("name") String name,@Param("introduction") String introduction,@Param("imageAddress") String imageAddress){
        log.info("update controller info : id={}, name={}, introduction={}, imageAddress={}",id,name,introduction,imageAddress );
        User user=new User(id, name, null, null, introduction, imageAddress);
        userService.update(user);
        return ReturnType.ok("更新成功");
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    ReturnType login(@Param("account") String account, @Param("password") String password, @Param("code") String code, HttpServletRequest httpServletRequest) {
        log.info("controller login. account={}, password={}, code={}", account, password, code);
        Map data = new HashMap();
        String verifyCode = redisTemplate.opsForValue().get(account);
        User user = new User();
        if (code != null && code.equals(verifyCode)) {
            log.info("{} login with code.", account);
            user = userService.userLogin(account, null);
            if (user == null || user.getId() == null)
                return ReturnType.failure("登录失败，验证码错误!");

        } else {
            log.info("{} login with password.", account);
            user = userService.userLogin(account, password);
            if (user == null || user.getId() == null)
                return ReturnType.failure("登录失败,密码错误!");

        }


        redisTemplate.opsForValue().set(httpServletRequest.getSession().getId(), user.getAccount(), MessageUtil.CACHE_TIME, TimeUnit.MINUTES);
        data.put("userId", user.getId());
        data.put("account", user.getAccount());
        data.put("name", user.getName());
        data.put("introduction", user.getIntroduction());
        data.put("imageAddress", user.getImageAddress());
        return ReturnType.ok("登录成功!", data);

    }

    @RequestMapping(value = "logout" ,method = RequestMethod.GET)
    ReturnType logout(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        String account = redisTemplate.opsForValue().get(session.getId());
        log.info("account: {} logout.",account);
        redisTemplate.delete(session.getId());
        httpServletRequest.getSession().invalidate();
        return ReturnType.ok("注销成功!");
    }


    /**
     * 获取验证码
     *
     * @param account            账号信息
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "verification", method = RequestMethod.GET)
    ReturnType verification(@Param("account") String account, HttpServletRequest httpServletRequest) {
        AccountType accountType = MessageUtil.accountType(account);
        HttpSession sessioin = httpServletRequest.getSession();
        String code = redisTemplate.opsForValue().get(account);
        Map data = new HashMap();
        data.put("account", account);
        if (code == null || code.equals("")) {
            code = MessageUtil.getVerificationCode();
            final String sendCode=code;
            if (accountType == AccountType.ERROR)
                return ReturnType.failure("用户名格式不规范: " + account);
            if (accountType == AccountType.PHONENUMBER) {
                Future<?> future = MessageUtil.executorService.submit(() -> {
                    MessageUtil.sendPhoneMessage(account, sendCode);
                });
                redisTemplate.opsForValue().set(account, code, MessageUtil.CACHE_TIME, TimeUnit.MINUTES);
                data.put("code", code);
                return ReturnType.ok("发送成功!", data);

            } else if (accountType == AccountType.EMAIL) {
                MessageUtil.executorService.submit(()->{
                    MessageUtil.sendEmail(mailSender, account, sendCode);
                });

                redisTemplate.opsForValue().set(account, code, MessageUtil.CACHE_TIME, TimeUnit.MINUTES);
                data.put("code", code);
                return ReturnType.ok("发送成功!", data);

            }
        }
        data.put("code", code);
        return ReturnType.ok("发送成功!", data);
    }


    @RequestMapping(value = "file",method = RequestMethod.POST)
    ReturnType test(@RequestParam("file")MultipartFile multipartFile){
//        System.out.println(JSON.toJSONString(multipartFile));
        Map resultMap=new HashMap();
        try {
            //未判断是否为图片
            System.out.println(MessageUtil.USER_DIR_TMP_PATH+multipartFile.getOriginalFilename());
            multipartFile.transferTo(Paths.get(MessageUtil.USER_DIR_TMP_PATH,multipartFile.getOriginalFilename()));
            resultMap.put("netPath", MessageUtil.NET_PATH+multipartFile.getOriginalFilename());
            return ReturnType.ok("上传成功",resultMap );
        } catch (IOException e) {
            e.printStackTrace();
            return ReturnType.failure("上传失败");
        }

    }


    @RequestMapping(value = "test", method = RequestMethod.GET)
    ReturnType test(HttpServletRequest httpServletRequest) {
        HttpSession sesssion = httpServletRequest.getSession();
        System.out.println("sessionId= " + sesssion.getId() + " , account= " + redisTemplate.opsForValue().get(sesssion.getId()));
        return ReturnType.ok("success");
    }


}