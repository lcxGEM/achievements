package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.IndexTalent;

import java.util.List;

public interface IIndexTalentService {

    List<IndexTalent> queryAll();

    IndexTalent queryById(int id);

    int insert(IndexTalent indexTalent);

    int update(IndexTalent indexTalent);

    int deleteById(int id);
}
