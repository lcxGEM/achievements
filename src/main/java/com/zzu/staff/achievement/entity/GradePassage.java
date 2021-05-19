package com.zzu.staff.achievement.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

/**
 * 论文
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradePassage {

    private Long passageId;

    private Long gradeId;

    private Integer level;

    private String passageName;

    private Integer schoolOrder;

    private Integer authorOrder;

    private Boolean isOne;

    private Integer oneNum;

    private Integer partition;

    private String journal;//期刊

    private String passageDate;

    private String passageUrl;

    private Float passageGrade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GradePassage)) return false;
        GradePassage passage = (GradePassage) o;
        return getPassageId().equals(passage.getPassageId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPassageId());
    }
}
