package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.mapper.UserMapper;
import com.zzu.staff.achievement.service.IUserService;
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
    public int update(User user) {
        return userMapper.update(user);
    }

    @Override
    public int deleteById(long id) {
        return userMapper.deleteById(id);
    }
}
