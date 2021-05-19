package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.UserRecord;
import com.zzu.staff.achievement.entity.UserRecordVo;

import java.util.List;

public interface IUserRecordService {

    List<UserRecordVo> queryAllVo(String sName, int teacherType, int gsType, int msType, int dsType, int depart);

    UserRecordVo queryById(long id);

    int insert(UserRecord staff);

    int deleteById(long id);

    int update(UserRecord  staff);

    int updateAll(int stId);

    UserRecord queryRecordByUsId(long id);
}
