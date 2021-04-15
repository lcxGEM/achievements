package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 获奖情况
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradePrize {

    private Long prizeId;

    private Long gradeId;

    private String prizeName;

    private Integer prizeType;

    private Integer prizeLevel;

    private Date prizeDate;

    private Integer prizeSchoolOrder;

    private Integer prizeAuthorOrder;

    private String prizeUrl;

    private Float prizeGrade;
}
