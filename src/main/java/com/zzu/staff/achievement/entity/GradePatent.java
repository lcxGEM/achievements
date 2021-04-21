package com.zzu.staff.achievement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 专利
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GradePatent {

    private Long patentId;

    private Long gradeId;

    private String patentName;

    private Integer nature;//专利性质 1 国际专利 2 发明专利

    private String patentNum;

    private Boolean isInventor;

    private String patentUrl;

    private Integer patentGrade;
}
