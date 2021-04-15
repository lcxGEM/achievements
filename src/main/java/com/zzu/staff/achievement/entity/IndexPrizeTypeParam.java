package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexPrizeTypeParam {

    private Integer prizeTypeId;

    private String prizeTypeName;

    private List<IndexPrizeLevel> prizeLevels;
}
