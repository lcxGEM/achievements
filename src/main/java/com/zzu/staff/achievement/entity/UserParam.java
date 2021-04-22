package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {

    private BigDecimal userId;

    private String userName;

    private Integer sex;

    private Integer peopleCategory; //在职和引进

    private String phoneNumber; //联系方式

    private String idcard;

    private Integer departmentId;

    private String departmentName;

    private Float sum;

    private BigDecimal  gradeId;

    private Integer year; //以2020年为基础，每年加一；根据年份区分不同成绩值，详细信息绑定gradeID

    private Float stu;

    private Float talent;

    private Float passage;

    private Float program;

    private Float prize;

    private Float patent;

    private Float result;

    public String getPeopleCategory() {
        if(peopleCategory==1){
            return "校内在职";
        }else {
            return "校外引进";
        }
    }

    public String getUserId() {
        return userId.toString();
    }

    public String getGradeId() {
        return gradeId.toString();
    }
}
