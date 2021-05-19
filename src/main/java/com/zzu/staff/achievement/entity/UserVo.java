package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private Long userId;

    private String userName;

    private Integer sex;

    private Integer peopleCategory; //在职和引进

    private String nativePlace; //籍贯

    private Integer politicsStatus; //政治面貌

    private String politicsName; //政治面貌名称

    private Integer nation; //岗位类别

    private String nationName; //岗位类别名称

    private String phoneNumber; //联系方式

    private String idcard;

    private Integer departmentId;

    private String departmentName;
}
