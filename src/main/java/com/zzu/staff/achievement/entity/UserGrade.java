package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGrade {

    private Long gradeId;

    private Long uId;//对应测评教师

    private Integer year; //以2020年为基础，每年加一；根据年份区分不同成绩值，详细信息绑定gradeID

    private Float stu; //指导博士、博士后

    private Float talent; //人才项目

    private Float passage; //论文

    private Float program; //科研项目

    private Float prize; //获奖

    private Float patent; //专利

    private Float result; //成果转化

    private Float sum; //业绩总分

    private Float indexSum; //业绩折合分

    private Float comSum; //学历总分

    private Float indexComSum; //学历折合分

    private Float resSum; //最终分

    private Boolean isRes; //是否合格

    private Integer status; //状态：1待审核，3学院审核不通过，2学院审核通过，4确认提交


}
