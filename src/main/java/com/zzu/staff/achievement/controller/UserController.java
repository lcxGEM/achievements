package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("queryByDepartment/{id}")
    public List<User> queryByDepartment(@PathVariable("id")Integer id){
        return  userService.queryByDepartment(id);
    }

    @GetMapping("queryById/{id}")
    public User queryById(@PathVariable("id")Long id){
        return userService.queryById(id);
    }

    @PostMapping("insert")
    public int insert(User user){
        return userService.insert(user);
    }

    @PostMapping("update")
    public int update(User user){
        return userService.update(user);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id){
        return userService.deleteById(id);
    }
}
