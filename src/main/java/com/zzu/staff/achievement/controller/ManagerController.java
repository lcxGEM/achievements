package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.Manager;
import com.zzu.staff.achievement.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager/")
public class ManagerController {

    @Autowired
    private IManagerService service;

    @GetMapping("doLogin/{id}/{passwd}")
    public Manager doLogin(@PathVariable("id")Long id,@PathVariable("passwd")String passwd){
        return service.doLogin(id,passwd);
    }
}
