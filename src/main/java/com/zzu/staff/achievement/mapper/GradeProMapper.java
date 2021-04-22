package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.GradePro;
import com.zzu.staff.achievement.entity.GradeProParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradeProMapper {

    List<GradePro> queryAll();

    List<GradeProParam> queryByGradeId(long id);

    GradePro queryById(long id);

    int insert(GradePro gradePro);

    int update(GradePro gradePro);

    int deleteById(long id);

    int deleteByGradeId(long id);
}
