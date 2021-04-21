package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.Department;
import com.zzu.staff.achievement.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department/")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping("queryAll")
    public List<Department> queryAll(){
        return departmentService.queryAll();
    }
}
