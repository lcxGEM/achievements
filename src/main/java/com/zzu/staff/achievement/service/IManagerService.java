package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.Manager;

import java.util.List;

public interface IManagerService {

    Manager doLogin(int id, String passwd);

    List<Manager> queryAll(String name, int department);

    Manager queryById(int id);

    int insert(Manager manager);

    int deleteById(int id);

    int update(Manager manager);

    int changePasswd(int id, String passwd);
}
