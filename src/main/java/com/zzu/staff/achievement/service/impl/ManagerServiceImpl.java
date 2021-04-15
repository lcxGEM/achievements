package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.Manager;
import com.zzu.staff.achievement.mapper.ManagerMapper;
import com.zzu.staff.achievement.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.WeakHashMap;

@Service
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private ManagerMapper mapper;

    @Override
    public Manager doLogin(long id, String passwd) {
        Manager manager = mapper.queryById(id);
        if(manager!=null){
            if(manager.getPasswd().equals(passwd)){
                return manager;
            }else {
                return null;
            }
        }else{
            return null;
        }
    }
}
