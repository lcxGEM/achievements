package com.zzu.staff.achievement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 论文
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradePassage {

    private Long passageId;

    private Long gradeId;

    private Integer level;

    private String passageName;

    private String doi;

    private Integer schoolOrder;

    private Integer authorOrder;

    private Boolean isOne;

    private Boolean oneNum;

    private Integer partition;

    private String journal;//期刊

    private Date passageDate;

    private String passageUrl;

    private Float passageGrade;
}
