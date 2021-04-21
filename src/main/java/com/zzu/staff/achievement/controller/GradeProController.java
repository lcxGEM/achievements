package com.zzu.staff.achievement.controller;


import com.zzu.staff.achievement.entity.GradePro;
import com.zzu.staff.achievement.entity.GradeProParam;
import com.zzu.staff.achievement.service.IGradeProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gradePro/")
public class GradeProController {

    @Autowired
    private IGradeProService service;

    @GetMapping("queryByGradeId/{id}")
    public List<GradeProParam> queryByGradeId(@PathVariable("id")Long id){
        return service.queryByGradeId(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id) throws Exception {
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(GradePro pro) throws Exception {
        return service.insertA(pro);
    }

    @PostMapping("update")
    public int update(GradePro pro) throws Exception {
        return service.update(pro);
    }
}
