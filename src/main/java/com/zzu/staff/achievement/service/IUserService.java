package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserParam;
import com.zzu.staff.achievement.entity.UserVo;

import java.util.List;

public interface IUserService {

    List<UserVo> queryAll(String name, String idCard, Integer nation, Integer category, Integer departId);

    List<User> queryByDepartment(int id);
    //显示基本信息
    User queryById(long id);

    User doLogin(String tel, String passwd);
    //注册
    long insertA(User user);
    //更新
    int update(User user) throws Exception;
    //删除
    int deleteById(long id);

    List<UserParam> queryAllParam(int year,int category,String sName,String sTel,String sId,int sDepart,int status);

    int changePasswd(Long id, String passwd);

    List<UserParam> queryUserParam(Long id);
}
