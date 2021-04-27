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

    private Float stu;

    private Float talent;

    private Float passage;

    private Float program;

    private Float prize;

    private Float patent;

    private Float result;

    private Float sum;

    private Integer status;
}
