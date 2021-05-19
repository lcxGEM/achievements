package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.SchoolType;

import java.util.List;

public interface SchoolTypeService {
    List<SchoolType> queryAll();

    SchoolType queryById(int id);

    int insert(SchoolType schoolType);

    int deleteById(int id);

    int update(SchoolType schoolType);
}
