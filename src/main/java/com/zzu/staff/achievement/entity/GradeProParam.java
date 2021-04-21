package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeProParam {

    private Long programId;

    private Long gradeId;

    private String proName;

    private String proNum;

    private Integer proLevel;

    private String proLevelName;

    private Boolean host;

    private String proUrl;

    private Float proGrade;
}
