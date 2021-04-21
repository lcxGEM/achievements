package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradePrizeParam {

    private Long prizeId;

    private Long gradeId;

    private String prizeName;

    private Integer prizeType;

    private String prizeTypeName;

    private Integer prizeLevel;

    private String prizeLevelName;

    private String prizeDate;

    private Integer prizeSchoolOrder;

    private Integer prizeAuthorOrder;

    private String prizeUrl;

    private Float prizeGrade;
}
