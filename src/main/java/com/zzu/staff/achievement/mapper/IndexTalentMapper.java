package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.IndexTalent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexTalentMapper {

    List<IndexTalent> queryAll();

    IndexTalent queryById(int id);

    int insert(IndexTalent indexTalent);

    int update(IndexTalent indexTalent);

    int deleteById(int id);
}
