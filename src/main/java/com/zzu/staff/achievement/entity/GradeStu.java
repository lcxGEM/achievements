package com.zzu.staff.achievement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 指导博士、博士后
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeStu {

    private Long stuGradeId;

    private Long gradeId;

    private Integer education; //1博士生 2博士后

    private String stuName;

    private String stuId;

    private boolean boxin;

    private boolean bote;

    private Float stuGrade;
}
