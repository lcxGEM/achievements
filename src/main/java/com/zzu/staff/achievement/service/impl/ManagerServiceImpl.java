package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.Manager;
import com.zzu.staff.achievement.mapper.ManagerMapper;
import com.zzu.staff.achievement.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.WeakHashMap;

@Service
public class ManagerServiceImpl implements IManagerService {

    @Autowired
    private ManagerMapper mapper;

    @Override
    public Manager doLogin(int id, String passwd) {
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

    @Override
    public List<Manager> queryAll(String name, int department) {

        String trim = name.trim();
        if(trim.length()==0&&department==-1){
            return mapper.queryAll();
        }else {
            return mapper.searchAll(trim,department);
        }
    }

    @Override
    public Manager queryById(int id) {
        return mapper.queryById(id);
    }

    @Override
    public int insert(Manager manager) {
        return mapper.insert(manager);
    }

    @Override
    public int deleteById(int id) {
        return mapper.deleteById(id);
    }

    @Override
    public int update(Manager manager) {
        return mapper.update(manager);
    }

    @Override
    public int changePasswd(int id, String passwd) {
        return mapper.changePasswd(id,passwd);
    }
}
