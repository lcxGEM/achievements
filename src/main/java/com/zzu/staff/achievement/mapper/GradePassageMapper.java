package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.GradePassage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradePassageMapper {

    List<GradePassage> queryAll();

    List<GradePassage> queryByGradeId(long id);

    GradePassage queryById(long id);

    int insert(GradePassage gradePassage);

    int update(GradePassage gradePassage);

    int deleteById(long id);

    int deleteByGradeId(long id);
}
