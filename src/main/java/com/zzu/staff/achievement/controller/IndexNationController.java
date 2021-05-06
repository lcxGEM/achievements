package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.IndexNation;
import com.zzu.staff.achievement.service.IndexNationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/indexNation/")
public class IndexNationController {

    @Autowired
    private IndexNationService service;

    @GetMapping("queryAll")
    public List<IndexNation> queryAll(){
        return service.queryAll();
    }

    @GetMapping("queryById/{id}")
    public IndexNation queryById(@PathVariable("id") Integer id){
        return service.queryById(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Integer id){
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(IndexNation nation){
        return service.insert(nation);
    }

    @PostMapping("update")
    public int update(IndexNation nation){
        return service.update(nation);
    }
}
