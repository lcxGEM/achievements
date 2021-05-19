package com.zzu.staff.achievement.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SchoolVo  implements Serializable {

    private Long id;

    private Integer sort;

    private String name;

    private Integer schoolType;

    private String typeName;
}
