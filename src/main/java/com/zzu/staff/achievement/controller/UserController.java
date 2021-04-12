package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("queryAll")
    public List<User> queryAll(){
        return userService.queryAll();
    }


}
