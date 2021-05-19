package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOut {

    private String userId;

    private String userName;

    private String passwd;

    private Integer nation; //岗位类别

    private Integer departmentId;
}
