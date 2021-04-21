package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 成果转化
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeResult {

    private Long resultId;

    private Long gradeId;

    private String resultName;

    private Float resultNum;

    private Float resultGrade;

    private String resultUrl;
}
