package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.GradePro;
import com.zzu.staff.achievement.entity.GradeProParam;

import java.util.List;

public interface IGradeProService {

    GradePro insert(GradePro pro);

    List<GradeProParam> queryByGradeId(long id);

    int deleteById(long id) throws Exception;

    int insertA(GradePro pro) throws Exception;

    int update(GradePro pro) throws Exception;
}
