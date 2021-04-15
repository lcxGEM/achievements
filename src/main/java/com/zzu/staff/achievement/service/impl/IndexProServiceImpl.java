package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.IndexPro;
import com.zzu.staff.achievement.mapper.IndexProMapper;
import com.zzu.staff.achievement.service.IIndexProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexProServiceImpl implements IIndexProService {

    @Autowired
    private IndexProMapper mapper;

    @Override
    public List<IndexPro> queryAll() {
        return mapper.queryAll();
    }

    @Override
    public IndexPro queryById(int id) {
        return mapper.queryById(id);
    }

    @Override
    public int insert(IndexPro indexPro) {
        return mapper.insert(indexPro);
    }

    @Override
    public int update(IndexPro indexPro) {
        return mapper.update(indexPro);
    }

    @Override
    public int deleteById(int id) {
        return mapper.deleteById(id);
    }
}
