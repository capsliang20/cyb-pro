package com.qwwaq.cyb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment implements Serializable {
    public static int PROJECT_COMMENT=1,ARTICLE_COMMENT=2,COMMENT_REPLY=3;
    private Integer id;
    private String content;
    private Integer type;
    private Integer targetId;
    private Integer userId;
    private String userName;

    private List<Comment> commentList;

}