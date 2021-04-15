package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.IndexPrizeLevel;
import com.zzu.staff.achievement.entity.IndexPrizeType;
import com.zzu.staff.achievement.entity.IndexPrizeTypeParam;

import java.util.List;
import java.util.Map;

public interface IIndexPrizeService {

    List<IndexPrizeTypeParam> queryAllTypeParam();

    List<IndexPrizeType> queryAllType();

    List<IndexPrizeLevel> queryLevelByType(int type);

    int deleteById(int id);

    int insert(Map<String, Object> param);

    int update(Map<String, Object> param);
}
