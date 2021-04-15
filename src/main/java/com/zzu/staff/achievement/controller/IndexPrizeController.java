package com.zzu.staff.achievement.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.IndexPrizeLevel;
import com.zzu.staff.achievement.entity.IndexPrizeType;
import com.zzu.staff.achievement.entity.IndexPrizeTypeParam;
import com.zzu.staff.achievement.entity.IndexPro;
import com.zzu.staff.achievement.service.IIndexPrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/indexPrize/")
public class IndexPrizeController {

    @Autowired
    private IIndexPrizeService service;


    @GetMapping("getParam/{pageNum}/{pageSize}")
    public PageInfo<IndexPrizeTypeParam> queryAll(@PathVariable("pageNum")int pageNum,
                                             @PathVariable("pageSize") int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<IndexPrizeTypeParam> list = service.queryAllTypeParam();
        PageInfo<IndexPrizeTypeParam> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @GetMapping("queryAllTypeNoParam")
    public List<IndexPrizeType> queryAll(){
        return service.queryAllType();
    }

    @GetMapping("queryLevelByType/{typeId}")
    public List<IndexPrizeLevel> queryLevelByType(@PathVariable("typeId")Integer id){
        return service.queryLevelByType(id);
    }


    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Integer id){
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(@RequestBody Map<String, Object> param){
        return service.insert(param);
    }

    @PostMapping("update")
    public int update(@RequestBody Map<String,Object> param){
        return service.update(param);
    }

}
