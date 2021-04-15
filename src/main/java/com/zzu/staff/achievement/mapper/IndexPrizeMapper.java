package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.IndexPrizeLevel;
import com.zzu.staff.achievement.entity.IndexPrizeType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexPrizeMapper {

    List<IndexPrizeType> queryAllType();

    List<IndexPrizeLevel> queryLevelByType(int typeId);

    IndexPrizeType queryTypeById(int id);

    IndexPrizeLevel queryLevelByTypeLevel(int level);

    int deleteTypeById(int id);

    int deleteLevelByType(int id);

    int deleteLevelById(int id);

    int insertType(IndexPrizeType indexPrizeType);

    int insertLevel(IndexPrizeLevel level);

    int updateType(IndexPrizeType indexPrizeType);

    int updateLevel(IndexPrizeLevel indexPrizeLevel);
}
