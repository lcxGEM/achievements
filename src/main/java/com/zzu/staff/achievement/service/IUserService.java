package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserParam;

import java.util.List;

public interface IUserService {

    List<User> queryAll();

    List<UserParam> queryAllParam(int year,int category,String sName,String sTel,String sId,int sDepart);

    List<User> queryByDepartment(int id);

    User queryById(long id);

    int insert(User user);

    long insertA(User user);

    int update(User user);

    int deleteById(long id);
}
