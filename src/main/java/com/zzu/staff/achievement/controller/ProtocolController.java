package com.zzu.staff.achievement.controller;

import com.zzu.staff.achievement.entity.Protocol;
import com.zzu.staff.achievement.service.IProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/protocol/")
public class ProtocolController {

    @Autowired
    private IProtocolService protocolService;


    @GetMapping("queryById/{id}")
    public Protocol queryById(@PathVariable("id")Integer id){
        return protocolService.queryById(id);
    }

    @PostMapping("update")
    public int update(Protocol protocol){
        return protocolService.update(protocol);
    }
}
