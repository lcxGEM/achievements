package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.mapper.UserGradeMapper;
import com.zzu.staff.achievement.service.IUserGradeService;
import com.zzu.staff.achievement.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserGradeServiceImpl implements IUserGradeService {

    @Autowired
    private UserGradeMapper mapper;

    @Override
    public List<UserGrade> queryByUId(long uId) {
        return mapper.queryByUser(uId);
    }

    @Override
    public UserGrade queryByUIdAndYear(long uId, int year) {
        return mapper.queryByUIdAndYear(uId,year);
    }

    @Override
    public UserGrade queryById(long id) {
        return mapper.queryById(id);
    }

    @Override
    public int deleteById(long id) {
        return mapper.deleteById(id);
    }

    @Override
    public int insert(UserGrade userGrade) {
        return mapper.insert(userGrade);
    }

    @Override
    public Long insertA(UserGrade userGrade) {
        Long id = IdWorker.getId();
        userGrade.setGradeId(id);
        if(mapper.insertA(userGrade)==1){
            return id;
        }else{
            return -1l;
        }
    }

    @Override
    public int update(UserGrade userGrade) {
        return mapper.update(userGrade);
    }
}
