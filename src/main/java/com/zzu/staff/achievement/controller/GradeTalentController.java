package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.GradeTalent;
import com.zzu.staff.achievement.service.IGradeTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gradeTalent/")
public class GradeTalentController {

    @Autowired
    private IGradeTalentService service;

    @GetMapping("queryByGradeId/{id}")
    public GradeTalent queryByGradeId(@PathVariable("id")Long id){
        return service.queryByGradeId(id);
    }

    @PostMapping("update")
    public int update(GradeTalent talent) throws Exception {
        return service.update(talent);
    }

    @PostMapping("insert")
    public int insert(GradeTalent talent) throws Exception {
        return service.insertA(talent);
    }
}
