package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long userId;

    private String userName;

    private String passwd;

    private Integer sex;

    private Integer peopleCategory; //在职和引进

    private String nativePlace; //籍贯

    private String politicsStatus; //政治面貌

    private String nation; //民族

    private String phoneNumber; //联系方式

    private String idcard;

    private Integer departmentId;
}
