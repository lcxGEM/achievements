package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.SchoolType;
import com.zzu.staff.achievement.service.SchoolTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schoolIndex")
public class SchoolTypeController {

    @Autowired
    private SchoolTypeService schoolTypeService;

    @GetMapping("/queryAll")
    public List<SchoolType> queryAll(){
        return schoolTypeService.queryAll();
    }

    @GetMapping("/delete/{id}")
    public int deleteById(@PathVariable("id")Integer id){
        return schoolTypeService.deleteById(id);
    }

    @PostMapping("/update")
    public int update(SchoolType schoolType){
        return schoolTypeService.update(schoolType);
    }

    @PostMapping("/add")
    public int add(SchoolType schoolType){
        return schoolTypeService.insert(schoolType);
    }

}
