package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.GradeStu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface GradeStuMapper {

    List<GradeStu> queryAll();

    List<GradeStu> queryByGradeId(long id);

    GradeStu queryById(long id);

    int insert(GradeStu gradeStu);

    int update(GradeStu gradeStu);

    int deleteByGradeId(long id);

    int deleteById(long id);
}
