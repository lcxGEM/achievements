package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.GradePatent;

import java.util.List;

public interface IGradePatentService {

    GradePatent insert(GradePatent patent);

    List<GradePatent> queryByGradeId(long id);

    int deleteById(long id) throws Exception;

    int insertA(GradePatent patent) throws Exception;

    int update(GradePatent patent) throws Exception;
}
