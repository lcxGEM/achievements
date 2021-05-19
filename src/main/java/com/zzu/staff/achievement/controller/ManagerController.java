package com.zzu.staff.achievement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.Manager;
import com.zzu.staff.achievement.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager/")
public class ManagerController {

    @Autowired
    private IManagerService service;

    @GetMapping("doLogin/{id}/{passwd}")
    public Manager doLogin(@PathVariable("id")Integer id,@PathVariable("passwd")String passwd){
        return service.doLogin(id,passwd);
    }

    @GetMapping("/queryAll/{pageNum}/{pageSize}/{name}/{department}")
    public PageInfo<Manager> queryAll(@PathVariable("pageNum")int pageNum,
                                      @PathVariable("pageSize") int pageSize,
                                      @PathVariable("name")String name,
                                      @PathVariable("department")int department){
        PageHelper.startPage(pageNum, pageSize);
        List<Manager> schoolVoList = service.queryAll(name,department);
        PageInfo<Manager> pageInfo = new PageInfo<>(schoolVoList);
        return pageInfo;
    }

    @GetMapping("/deleteById/{id}")
    public int deleteById(@PathVariable("id")Integer id){
        return service.deleteById(id);
    }

    @PostMapping("/insert")
    public int insert(Manager manager){
        return service.insert(manager);
    }

    @PostMapping("/updateById")
    public int updateById(Manager manager){
        return service.update(manager);
    }

    @GetMapping("/changePasswd/{id}/{passwd}")
    public int changePasswd(@PathVariable("id")int id,@PathVariable("passwd")String passwd){
        return service.changePasswd(id,passwd);
    }
}
