package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.GradeResult;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradeResultMapper {

    List<GradeResult> queryAll();

    List<GradeResult> queryByGradeId(long id);

    GradeResult queryById(long id);

    int insert(GradeResult gradeResult);

    int update(GradeResult gradeResult);

    int deleteById(long id);
}
