package com.qwwaq.cyb.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private String module;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;
    private Integer creatorId;
    private String creatorName;
    private String creatorImage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Comment> commentList;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isFollowed;

    private Integer followerNum;
    private Integer commentNum;

}
