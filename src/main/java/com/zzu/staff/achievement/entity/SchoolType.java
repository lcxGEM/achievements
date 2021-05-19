package com.zzu.staff.achievement.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SchoolType {

    private Integer id; //编号

    private String typeName; //学校类型名

    private Float schoolIndex; //学校系数

    private Float undergraduateIndex;

    private Float masterIndex;

    private Float doctorIndex;

    private Float sumIndex;  //综合系数

    private Float aschoolIndex; //学校系数

    private Float aundergraduateIndex;

    private Float amasterIndex;

    private Float adoctorIndex;

    private Float asumIndex;  //综合系数

    public Float getSumIndex() {
        Float sum = this.doctorIndex*this.masterIndex*this.undergraduateIndex;
        BigDecimal b = new BigDecimal(sum);
        float f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
        this.sumIndex = f1;
        return sumIndex;
    }

    public Float getAsumIndex() {
        Float sum = this.adoctorIndex*this.amasterIndex*this.aundergraduateIndex;
        BigDecimal b = new BigDecimal(sum);
        float f1 = b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
        this.asumIndex = f1;
        return asumIndex;
    }
}
