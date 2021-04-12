package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.User;

import java.util.List;

public interface IUserService {

    List<User> queryAll();

    List<User> queryByDepartment(int id);

    User queryById(long id);

    int insert(User user);

    int update(User user);

    int deleteById(long id);
}
