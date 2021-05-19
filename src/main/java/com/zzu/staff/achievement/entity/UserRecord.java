package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRecord {

    private Long recordId;

    private Long usId;//用户ID

    private Long undergraduateSchool;

    private Long masterSchool;

    private Long doctorSchool;

    private Float compositeIndex;

    private String evaluation; //评价
}
