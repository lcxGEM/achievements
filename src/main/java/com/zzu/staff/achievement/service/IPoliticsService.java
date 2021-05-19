package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.Politics;

import java.util.List;

public interface IPoliticsService {

    List<Politics> queryAll();

    Politics queryById(int id);

    int insert(Politics politics);

    int update(Politics politics);

    int deleteById(int id);
}
