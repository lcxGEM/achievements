package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.IndexNation;

import java.util.List;

public interface IndexNationService {

    List<IndexNation> queryAll();

    IndexNation queryById(int id);

    int deleteById(int id);

    int update(IndexNation indexNation);

    int insert(IndexNation indexNation);
}
