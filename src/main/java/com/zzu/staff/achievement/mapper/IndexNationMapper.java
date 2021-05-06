package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.IndexNation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexNationMapper {

    List<IndexNation> queryAll();

    IndexNation queryById(int id);

    int deleteById(int id);

    int update(IndexNation indexNation);

    int insert(IndexNation indexNation);
}
