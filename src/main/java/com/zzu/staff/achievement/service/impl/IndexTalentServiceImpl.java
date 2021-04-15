package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.IndexTalent;
import com.zzu.staff.achievement.mapper.IndexTalentMapper;
import com.zzu.staff.achievement.service.IIndexTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexTalentServiceImpl implements IIndexTalentService {

    @Autowired
    private IndexTalentMapper mapper;

    @Override
    public List<IndexTalent> queryAll() {
        return mapper.queryAll();
    }

    @Override
    public IndexTalent queryById(int id) {
        return mapper.queryById(id);
    }

    @Override
    public int insert(IndexTalent indexTalent) {
        return mapper.insert(indexTalent);
    }

    @Override
    public int update(IndexTalent indexTalent) {
        return mapper.update(indexTalent);
    }

    @Override
    public int deleteById(int id) {
        return mapper.deleteById(id);
    }
}
