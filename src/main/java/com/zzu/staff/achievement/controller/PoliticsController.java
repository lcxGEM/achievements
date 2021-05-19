package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.Politics;
import com.zzu.staff.achievement.service.IPoliticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/politics/")
public class PoliticsController {

    @Autowired
    private IPoliticsService service;

    @GetMapping("queryAll")
    public List<Politics> queryAll(){
        return service.queryAll();
    }

    @GetMapping("queryById/{id}")
    public Politics queryById(@PathVariable("id") Integer id){
        return service.queryById(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Integer id){
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(Politics politics){
        return service.insert(politics);
    }

    @PostMapping("update")
    public int update(Politics politics){
        return service.update(politics);
    }
}