package com.qwwaq.cyb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -2023285670801013939L;
    private Integer id;
    private String name;
    private String account;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private String introduction;
    private String imageAddress;

    private Integer concernedNum;
    private Integer followerNum;
}
