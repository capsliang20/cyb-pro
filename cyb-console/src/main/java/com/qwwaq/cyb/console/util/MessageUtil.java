package com.qwwaq.cyb.console.util;


import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

@Slf4j
public class MessageUtil {


    public static void main(String[] args) throws IOException {
      // System.out.println(Paths.get(USER_DIR_TMP_PATH).toFile().mkdir());
    }



    public static int PHONENUMBER_LENGTH=11;
    public static int CACHE_TIME=15;
    private static char[] codeArray={'0','1','2','3','4','5','6','7','8','9'};
    private static Random random=new Random();
    public static String USER_DIR_TMP_PATH=System.getProperty("user.dir")+"/tmp/";
    public static String NET_PATH="www.qwwaq.com:8088/";
    public static ExecutorService executorService=Executors.newCachedThreadPool();

    static {
        Path path=Paths.get(USER_DIR_TMP_PATH);
        log.info("USER_DIR_TMP_PATH={}",USER_DIR_TMP_PATH );
        if(!path.toFile().exists()){
            log.info("USER_DIR_TMP_PATH mkdir");
            path.toFile().mkdir();
        }
    }

   public static void isTmpDirExist(){
       Path path=Paths.get(USER_DIR_TMP_PATH);
       if(!path.toFile().exists()){
           path.toFile().mkdir();
       }
   }

    /**
     *
     * @param account
     * @return 0 : 电话号码 1:邮箱号码 2: 格式错误
     */
    public static AccountType accountType(String account){
        if(account==null||account.equals(""))
            return AccountType.ERROR;
        else if(account.length()==PHONENUMBER_LENGTH&&!Pattern.matches(".*\\D.*", account))
            return AccountType.PHONENUMBER;
        else if(Pattern.matches("\\w+@\\w+.\\w+", account))
            return AccountType.EMAIL;
        else return AccountType.ERROR;
    }

    public static String getVerificationCode(){
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<6;i++)
            stringBuilder.append(codeArray[random.nextInt(10)]);
        return stringBuilder.toString();
    }


    public static boolean sendEmail(JavaMailSender mailSender, String to, String key){
        try {
            SpringTemplateEngine thymeleaf=new SpringTemplateEngine();
            MimeMessage message=mailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setFrom("qwwaq@qq.com");
            helper.setTo(to);
            helper.setSubject("创业帮");
            Context ctx=new Context();
            ctx.setVariable("password", key);
            String emailText=thymeleaf.process("<!doctype html><html><body>验证码：<i><span th:text=\"${password}\"></span></i>。请勿告诉他人。(十五分钟有效)</body></html>", ctx);
            helper.setText(emailText,true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //手机短信
    public static boolean sendPhoneMessage(String phoneNumber,String key){//phoneNumber name
        final String product="Dysmsapi";
        final String domain = "dysmsapi.aliyuncs.com";

        final String accessKeyId="LTAIcxkZHWikXqxx";
        final String accessKeySecret="5esNdJU081PPJwl6btmnNmYrmi2UKg";


        try {
            IClientProfile profile=DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            SendSmsRequest request=new SendSmsRequest();
            request.setMethod(MethodType.POST);

            request.setPhoneNumbers(phoneNumber);
            request.setSignName("心愿瓶");
            request.setTemplateCode("SMS_137655525");
            request.setTemplateParam("{\"code\":\""+key+"\"}");

            SendSmsResponse sendSmsResponse=acsClient.getAcsResponse(request);
            if(sendSmsResponse.getCode()!=null&&sendSmsResponse.getCode().equals("OK")){
                return true;
            }
            else
                System.out.println(JSON.toJSONString(sendSmsResponse));
            return false;
        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return false;
        }
    }




}
