package com.zzu.staff.achievement.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zzu.staff.achievement.util.CustomerDoubleSerialize;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserRecordVo implements Serializable {

    private Long recordId;

    private String userId;

    private Integer nation; //教师类型

    private String nationName;

    private String userName;

    private String idcard;

    private Integer sex;

    private String phoneNumber;

    private Long undergraduateSchool;

    private Long masterSchool;

    private Long doctorSchool;

    private String uSchoolName;

    private String mSchoolName;

    private String dSchoolName;

    @JsonSerialize(using = CustomerDoubleSerialize.class)
    private Float compositeIndex;

    private String evaluation; //评价

    private String departmentName;

    private Integer departmentId;

    public Long getUndergraduateSchool() {
        if(undergraduateSchool!=null){
            return this.undergraduateSchool;
        }else{
            return new Long(0);
        }
    }

    public Long getMasterSchool() {
        if(masterSchool!=null){
            return this.masterSchool;
        }else {
            return new Long(0);
        }
    }

    public Long getDoctorSchool() {
        if(doctorSchool!=null) {
            return this.doctorSchool;
        }else {
            return new Long(0);
        }
    }

    public String getuSchoolName() {
        return uSchoolName!=null? uSchoolName:"无";
    }

    public String getmSchoolName() {
        return mSchoolName!=null?mSchoolName:"无";
    }

    public String getdSchoolName() {
        return dSchoolName!=null?dSchoolName:"无";
    }
}
