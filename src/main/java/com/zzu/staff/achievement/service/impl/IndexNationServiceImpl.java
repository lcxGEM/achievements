package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.IndexNation;
import com.zzu.staff.achievement.mapper.IndexNationMapper;
import com.zzu.staff.achievement.service.IndexNationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexNationServiceImpl implements IndexNationService {

    @Autowired
    private IndexNationMapper mapper;

    @Override
    public List<IndexNation> queryAll() {
        return mapper.queryAll();
    }

    @Override
    public IndexNation queryById(int id) {
        return mapper.queryById(id);
    }

    @Override
    public int deleteById(int id) {
        return mapper.deleteById(id);
    }

    @Override
    public int update(IndexNation indexNation) {
        return mapper.update(indexNation);
    }

    @Override
    public int insert(IndexNation indexNation) {
        return mapper.insert(indexNation);
    }
}
