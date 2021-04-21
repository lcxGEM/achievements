package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.UserGrade;

import java.util.List;
import java.util.Map;

public interface IUserGradeService {

    List<UserGrade> queryByUId(long uId);

    UserGrade queryByUIdAndYear(long uId,int year);

    UserGrade queryById(long id);

    int deleteById(long id);

    int insert(UserGrade userGrade);

    Long insertA(UserGrade userGrade);

    int update(UserGrade userGrade);
}
