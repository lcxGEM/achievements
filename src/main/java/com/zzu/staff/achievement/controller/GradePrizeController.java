package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.GradePrize;
import com.zzu.staff.achievement.entity.GradePrizeParam;
import com.zzu.staff.achievement.service.IGradePrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proPrize/")
public class GradePrizeController {

    @Autowired
    private IGradePrizeService service;

    @GetMapping("queryByGradeId/{id}")
    public List<GradePrizeParam> queryByGradeId(@PathVariable("id")Long id){
        return service.queryByGradeId(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id) throws Exception {
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(GradePrize prize) throws Exception {
        return service.insertA(prize);
    }

    @PostMapping("update")
    public int update(GradePrize prize) throws Exception {
        return service.update(prize);
    }
}
