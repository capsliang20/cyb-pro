package com.qwwaq.cyb.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project implements Serializable {
    private static final long serialVersionUID = 8820678050985046949L;
    private Integer id;
    private String name;
    private String introduction;
    private String imageAddress;
    private String module;

    private Integer creatorId;
    private String creatorName;
    private String creatorImage;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Comment> commentList;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isFollowed;

    private Integer followerNum;
    private Integer commentNum;


}
