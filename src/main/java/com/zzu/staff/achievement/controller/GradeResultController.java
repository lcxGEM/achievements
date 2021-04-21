package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.GradeResult;
import com.zzu.staff.achievement.service.IGradeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gradeResult/")
public class GradeResultController {

    @Autowired
    private IGradeResultService service;

    @GetMapping("queryByGradeId/{id}")
    public List<GradeResult> queryByGradeId(@PathVariable("id")Long id){
        return service.queryByGradeId(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id)throws Exception{
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(GradeResult result)throws Exception{
        return service.insertA(result);
    }

    @PostMapping("update")
    public int update(GradeResult result)throws Exception{
        return service.update(result);
    }
}
