package com.zzu.staff.achievement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 科研项目及经费
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradePro {

    private Long programId;

    private Long gradeId;

    private String proName;

    private String proNum;

    private Integer proLevel;

    private Boolean host;

    private String proUrl;

    private Float proGrade;
}
