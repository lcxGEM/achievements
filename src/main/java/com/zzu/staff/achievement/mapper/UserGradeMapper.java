package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.UserGrade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserGradeMapper {

    List<UserGrade> queryAll();

    UserGrade queryById(long id); //查特定年份

    List<UserGrade> queryByUser(long id); //根据用户查看所有年限

    int insert(UserGrade userGrade);

    int update(UserGrade userGrade);

    int deleteById(long id);
}
