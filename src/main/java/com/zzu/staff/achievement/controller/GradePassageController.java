package com.zzu.staff.achievement.controller;


import com.zzu.staff.achievement.entity.GradePassage;
import com.zzu.staff.achievement.service.IGradePassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gradePassage/")
public class GradePassageController {

    @Autowired
    private IGradePassageService passageService;

    @GetMapping("queryByGradeId/{id}")
    public List<GradePassage> queryByGradeId(@PathVariable("id")Long id){
        return passageService.queryByGradeId(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id) throws Exception {
        return passageService.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(GradePassage passage) throws Exception {
        return passageService.insertA(passage);
    }

    @PostMapping("update")
    public int update(GradePassage passage) throws Exception {
        return passageService.update(passage);
    }
}
