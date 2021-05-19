package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.IndexTalent;
import com.zzu.staff.achievement.entity.Politics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PoliticsMapper {

    List<Politics> queryAll();

    Politics queryById(int id);

    int insert(Politics politics);

    int update(Politics politics);

    int deleteById(int id);
}
