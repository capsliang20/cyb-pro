package com.qwwaq.cyb.entity;


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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer creatorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String creatorName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Comment> commentList;

    private Integer followerNum;
    private Integer commentNum;

}
