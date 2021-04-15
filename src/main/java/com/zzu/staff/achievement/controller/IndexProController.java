package com.zzu.staff.achievement.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.IndexPro;
import com.zzu.staff.achievement.service.IIndexProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 科研项目及经费指数
 */
@RestController
@RequestMapping("/indexPro/")
public class IndexProController {

    @Autowired
    private IIndexProService service;

    @GetMapping("queryAllPage/{pageNum}/{pageSize}")
    public PageInfo<IndexPro> queryAll(@PathVariable("pageNum")int pageNum,
                                       @PathVariable("pageSize") int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<IndexPro> list = service.queryAll();
        PageInfo<IndexPro> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @GetMapping("queryAllNoParam")
    public List<IndexPro> queryAll(){
        return service.queryAll();
    }

    @GetMapping("queryById/{id}")
    public IndexPro queryById(@PathVariable("id")Integer id){
        return service.queryById(id);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Integer id){
        return service.deleteById(id);
    }

    @PostMapping("insert")
    public int insert(IndexPro indexPro){
        return service.insert(indexPro);
    }

    @PostMapping("update")
    public int update(IndexPro indexPro){
        return service.update(indexPro);
    }
}
