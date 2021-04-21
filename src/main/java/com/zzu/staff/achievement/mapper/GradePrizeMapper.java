package com.zzu.staff.achievement.mapper;


import com.zzu.staff.achievement.entity.GradePrize;
import com.zzu.staff.achievement.entity.GradePrizeParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradePrizeMapper {

    List<GradePrize> queryAll();

    List<GradePrizeParam> queryByGradeId(long id);

    GradePrize queryById(long id);

    int insert(GradePrize gradePrize);

    int update(GradePrize gradePrize);

    int deleteById(long id);
}
