package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.Politics;
import com.zzu.staff.achievement.mapper.PoliticsMapper;
import com.zzu.staff.achievement.service.IPoliticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticsServiceImpl implements IPoliticsService {

    @Autowired
    private PoliticsMapper mappper;

    @Override
    public List<Politics> queryAll() {
        return mappper.queryAll();
    }

    @Override
    public Politics queryById(int id) {
        return mappper.queryById(id);
    }

    @Override
    public int insert(Politics politics) {
        return mappper.insert(politics);
    }

    @Override
    public int update(Politics politics) {
        return mappper.update(politics);
    }

    @Override
    public int deleteById(int id) {
        return mappper.deleteById(id);
    }
}
