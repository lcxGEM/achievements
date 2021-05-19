package com.zzu.staff.achievement.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzu.staff.achievement.util.CustomerDoubleSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {

    private Long userId;

    private String userName;

    private Integer sex;

    private Integer peopleCategory; //在职和引进

    private String nationName; //岗位类别

    private String phoneNumber; //联系方式

    private String idcard;

    private Integer departmentId;

    private String departmentName;

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float sum; //业绩分

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float indexSum; //业绩折合分

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float comSum; //学历分

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float indexComSum; //学历折合分

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float resSum; //折合后的总分

    private Boolean isRes;//是否合格

    private Long  gradeId;

    private Integer year;

    private Float stu;

    private Float talent;

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float passage;

    private Float program;

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float prize;

    private Float patent;

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float result;

    private Integer status;

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

    public Float getSum() {
        return sum==null?0:sum;
    }

    public Float getIndexSum() {
        return indexSum==null?0:indexSum;
    }

    public Float getComSum() {
        return comSum==null?0:comSum;
    }

    public Float getIndexComSum() {
        return indexComSum==null?0:indexComSum;
    }

    public Float getResSum() {
        return resSum==null?0:resSum;
    }
}
