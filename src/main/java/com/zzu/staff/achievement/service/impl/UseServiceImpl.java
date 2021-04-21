package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserParam;
import com.zzu.staff.achievement.mapper.UserMapper;
import com.zzu.staff.achievement.service.IUserService;
import com.zzu.staff.achievement.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UseServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> queryAll() {
        return userMapper.queryAll();
    }

    @Override
    public List<UserParam> queryAllParam(int year,int category, String sName, String sTel, String sId, int sDepart) {
        sName = sName.trim();
        sTel = sTel.trim();
        sId = sId.trim();
        if(sDepart<1){
            return userMapper.queryAllParam(year,category,sName,sTel,sId);
        }else {
            return userMapper.queryAllParamByParam(year,category,sName,sTel,sId,sDepart);
        }
    }

    @Override
    public List<User> queryByDepartment(int id) {
        return userMapper.queryByDepartment(id);
    }

    @Override
    public User queryById(long id) {
        return userMapper.queryById(id);
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public long insertA(User user) {
        long id = IdWorker.getId();
        user.setUserId(id);
        if(userMapper.insertA(user)==1){
            return id;
        }else {
            return -1;
        }
    }

    @Override
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int deleteById(long id) {
        return userMapper.deleteById(id);
    }
}
