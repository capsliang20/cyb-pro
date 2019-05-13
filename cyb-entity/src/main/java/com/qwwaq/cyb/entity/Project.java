package com.qwwaq.cyb.entity;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer creatorId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String creatorName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date createDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Comment> commentList;

    private Integer followerNum;
    private Integer commentNum;


}
