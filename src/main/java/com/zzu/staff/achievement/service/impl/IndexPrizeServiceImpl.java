package com.zzu.staff.achievement.service.impl;


import com.zzu.staff.achievement.entity.IndexPrizeLevel;
import com.zzu.staff.achievement.entity.IndexPrizeType;
import com.zzu.staff.achievement.entity.IndexPrizeTypeParam;
import com.zzu.staff.achievement.mapper.IndexPrizeMapper;
import com.zzu.staff.achievement.service.IIndexPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class IndexPrizeServiceImpl implements IIndexPrizeService {

    @Autowired
    private IndexPrizeMapper mapper;

    @Override
    public List<IndexPrizeTypeParam> queryAllTypeParam() {
        List<IndexPrizeType> list = mapper.queryAllType();
        List<IndexPrizeTypeParam> result = new ArrayList<>();
        for (IndexPrizeType type:list) {
            List<IndexPrizeLevel> levels = mapper.queryLevelByType(type.getPrizeTypeId());
             IndexPrizeTypeParam param = new IndexPrizeTypeParam(type.getPrizeTypeId(),
                     type.getPrizeTypeName(), levels);
             result.add(param);
        }
        return result;
    }

    @Override
    public List<IndexPrizeType> queryAllType() {
        return mapper.queryAllType();
    }

    @Override
    public List<IndexPrizeLevel> queryLevelByType(int type) {
        return mapper.queryLevelByType(type);
    }

    @Override
    public int deleteById(int id) {
        int a = 0;
        a+=mapper.deleteTypeById(id);
        a+=mapper.deleteLevelByType(id);
        return a;
    }

    @Override
    public int insert(Map<String, Object> param) {

        IndexPrizeType type = new IndexPrizeType(null, (String) param.get("prizeTypeName"));
        mapper.insertType(type);
        System.out.println(type.toString());
        int i = 1;
        List<Map<String,Object>> obj = (ArrayList<Map<String,Object>>) param.get("prizeLevels");
        for (Map<String,Object> levelO: obj) {
            IndexPrizeLevel level = new IndexPrizeLevel(null,type.getPrizeTypeId(),
                    (String) levelO.get("prizeLevelName"),
                    new Integer((String) levelO.get("prizeLevelGrade")));
            System.out.println(level.toString());
            mapper.insertLevel(level);
            i++;
        }
        return i;
    }

    @Override
    public int update(Map<String, Object> param) {
        IndexPrizeType type = new IndexPrizeType((Integer) param.get("prizeTypeId"), (String) param.get("prizeTypeName"));
        mapper.updateType(type);
        mapper.deleteLevelByType(type.getPrizeTypeId());
        int i = 1;
        List<Map<String,Object>> obj = (ArrayList<Map<String,Object>>) param.get("prizeLevels");
        for (Map<String,Object> levelO: obj) {
            IndexPrizeLevel level = new IndexPrizeLevel(null,type.getPrizeTypeId(),
                    (String) levelO.get("prizeLevelName"),
                    new Integer((String) levelO.get("prizeLevelGrade")));
            mapper.insertLevel(level);
            i++;
        }
        return i;
    }
}
