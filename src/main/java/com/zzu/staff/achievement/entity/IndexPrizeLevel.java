package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexPrizeLevel {

    private Integer prizeLevelId;

    private Integer prizeTypeId;

    private String prizeLevelName;

    private Integer prizeLevelGrade;

}
