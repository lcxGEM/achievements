package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.UserGrade;

public interface IUserGradeService {

    UserGrade queryByUIdAndYear(long uId,int year);

    UserGrade queryById(long id);

    int deleteById(long id);

    //int insert(UserGrade userGrade);

    Long insertA(UserGrade userGrade);

    int update(UserGrade userGrade);

    int updateStatus(Long id, Integer status);
}
