package com.qwwaq.cyb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReturnType {
    int code;
    String msg;
    Map data;

    public static ReturnType ok(String msg){
        return new ReturnType(200,msg,new HashMap());
    }

    public static ReturnType ok(String msg,Map data){
        return new ReturnType(200,msg,data);
    }

    public static ReturnType failure(String msg){
        return new ReturnType(400,msg,new HashMap());
    }

    public static ReturnType failure(String msg,Map data){
        return new ReturnType(400,msg,data);
    }

}
