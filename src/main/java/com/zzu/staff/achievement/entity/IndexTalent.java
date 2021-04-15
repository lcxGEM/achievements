package com.zzu.staff.achievement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 人才项目扩展类
 * 人才项目指数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexTalent {

    private Integer talentId;

    private String talentName;

    private Float grade;
}
