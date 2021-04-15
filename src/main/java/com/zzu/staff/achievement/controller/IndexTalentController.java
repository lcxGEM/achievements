package com.zzu.staff.achievement.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.IndexTalent;
import com.zzu.staff.achievement.service.IIndexTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人才项目指数
 */
@RestController
@RequestMapping("/indexTalent/")
public class IndexTalentController {

    @Autowired
    private IIndexTalentService service;

    @GetMapping("queryAll/{pageNum}/{pageSize}")
    public PageInfo<IndexTalent> queryAll(@PathVariable("pageNum")int pageNum,
                                      @PathVariable("pageSize") int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<IndexTalent> list = service.queryAll();
        PageInfo<IndexTalent> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @GetMapping("queryAllNoParam")
    public List<IndexTalent> queryAll(){
        return service.queryAll();
    }


    @GetMapping("queryById/{id}")
    public IndexTalent queryById(@PathVariable("id")Integer id){
        return service.queryById(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Integer id){
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(IndexTalent talent){
        return service.insert(talent);
    }

    @PostMapping("update")
    public int update(IndexTalent talent){
        System.out.println(talent.toString());
        return service.update(talent);
    }
}
