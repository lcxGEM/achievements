package com.zzu.staff.achievement.controller;


import com.zzu.staff.achievement.entity.GradePatent;
import com.zzu.staff.achievement.service.IGradePatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gradePatent/")
public class GradePatentController {

    @Autowired
    private IGradePatentService service;

    @GetMapping("queryByGradeId/{id}")
    public List<GradePatent> queryByGradeId(@PathVariable("id")Long id){
        return service.queryByGradeId(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id) throws Exception{
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(GradePatent patent)throws Exception{
        return service.insertA(patent);
    }

    @PostMapping("update")
    public int update(GradePatent patent)throws Exception{
        return service.update(patent);
    }

}
