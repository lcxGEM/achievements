package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.GradePrize;
import com.zzu.staff.achievement.entity.GradePrizeParam;

import java.util.List;

public interface IGradePrizeService {

    GradePrize insert(GradePrize prize);

    List<GradePrizeParam> queryByGradeId(long id);

    int deleteById(long id) throws Exception;

    int insertA(GradePrize prize) throws Exception;

    int update(GradePrize prize) throws Exception;
}
