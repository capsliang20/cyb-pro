package com.qwwaq.cyb.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private Date createDate;
    private Integer creatorId;
    private String creatorName;
    private String module;
}
