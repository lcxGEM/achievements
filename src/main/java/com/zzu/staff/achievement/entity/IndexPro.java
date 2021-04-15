package com.zzu.staff.achievement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 科研项目及经费
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexPro {

    private Integer proLevelId;

    private String proLevelName;

    private Integer proLevelGrade;
}
