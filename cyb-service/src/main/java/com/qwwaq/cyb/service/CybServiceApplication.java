package com.qwwaq.cyb.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.apache.dubbo.config.spring.context.annotation.EnableDubboConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

import java.io.IOException;

@SpringBootApplication
@ImportResource(value ="classpath:spring/cyb-dubbo-provider.xml")
@Slf4j
public class CybServiceApplication {

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(CybServiceApplication.class, args);
        while(true){
            try{
                Thread.sleep(Long.MAX_VALUE);
            } catch (InterruptedException e) {
                log.info("进程意外中断");
            }
        }
//        System.in.read();
    }


}
