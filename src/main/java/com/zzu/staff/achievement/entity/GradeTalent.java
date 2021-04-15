package com.zzu.staff.achievement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人才项目
 * 有关联选项：IndexTalent
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradeTalent {

    private Long gradeId;

    private Integer talentId;

    private String gradeUrl;
}
