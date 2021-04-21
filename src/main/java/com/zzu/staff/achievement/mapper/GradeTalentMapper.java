package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.GradeTalent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradeTalentMapper {

    List<GradeTalent> queryAll();

    GradeTalent queryById(long id);

    int insert(GradeTalent gradeTalent);

    int update(GradeTalent gradeTalent);

    int deleteById(long id);
}
