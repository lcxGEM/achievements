package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.IndexPro;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexProMapper {

    List<IndexPro> queryAll();

    IndexPro queryById(int id);

    int insert(IndexPro indexPro);

    int update(IndexPro indexPro);

    int deleteById(int id);
}
