package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexNation {

    private Integer nationId;

    private String nationName;

    private Integer nationCode;  //换算的比例

    private Integer nationLevel; //多少分达到这个指标
}
