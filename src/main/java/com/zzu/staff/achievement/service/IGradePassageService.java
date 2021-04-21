package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.GradePassage;

import java.util.List;

public interface IGradePassageService {

    GradePassage insert(GradePassage passage);

    List<GradePassage> queryByGradeId(long id);

    int deleteById(long id) throws Exception;

    int insertA(GradePassage passage) throws Exception;

    int update(GradePassage passage) throws Exception;
}
