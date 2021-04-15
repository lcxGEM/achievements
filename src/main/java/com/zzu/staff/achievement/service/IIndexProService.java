package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.IndexPro;

import java.util.List;

public interface IIndexProService {

    List<IndexPro> queryAll();

    IndexPro queryById(int id);

    int insert(IndexPro indexPro);

    int update(IndexPro indexPro);

    int deleteById(int id);
}
