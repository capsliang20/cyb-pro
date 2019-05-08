package com.qwwaq.cyb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module implements Serializable {
    private static final long serialVersionUID = -6518894577089820934L;
    private Integer id;
    private String name;
}
