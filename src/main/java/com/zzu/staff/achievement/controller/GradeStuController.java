package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.GradeStu;
import com.zzu.staff.achievement.service.IGradeStuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gradeStu/")
public class GradeStuController {

    @Autowired
    private IGradeStuService service;

    @GetMapping("queryByGradeId/{id}")
    public List<GradeStu> queryByGradeId(@PathVariable("id")Long id){
        return service.queryByGradeId(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id) throws Exception {
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(GradeStu stu) throws Exception {
        System.out.println(stu.toString());
        return service.insertS(stu);
    }

    @PostMapping("update")
    public int update(GradeStu stu) throws Exception {
        System.out.println(stu.toString());
        return service.update(stu);
    }
}
