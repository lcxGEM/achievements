package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.GradePatent;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradePatentMapper {

    List<GradePatent> queryAll();

    List<GradePatent> queryByGradeId(long id);

    GradePatent queryById(long id);

    int insert(GradePatent gradePatent);

    int update(GradePatent gradePatent);

    int deleteById(long id);
}
