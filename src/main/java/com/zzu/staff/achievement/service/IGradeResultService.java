package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.GradeResult;

import java.util.List;

public interface IGradeResultService {

    GradeResult insert(GradeResult result);

    List<GradeResult> queryByGradeId(Long id);

    int deleteById(Long id) throws Exception;

    int insertA(GradeResult result) throws Exception;

    int update(GradeResult result) throws Exception;
}
