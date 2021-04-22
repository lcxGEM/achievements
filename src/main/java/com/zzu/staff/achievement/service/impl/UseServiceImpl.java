package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.entity.UserParam;
import com.zzu.staff.achievement.mapper.*;
import com.zzu.staff.achievement.service.IUserService;
import com.zzu.staff.achievement.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UseServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private GradePassageMapper passageMapper;

    @Autowired
    private GradePatentMapper patentMapper;

    @Autowired
    private GradePrizeMapper prizeMapper;

    @Autowired
    private GradeProMapper proMapper;

    @Autowired
    private GradeResultMapper resultMapper;

    @Autowired
    private GradeStuMapper stuMapper;

    @Autowired
    private GradeTalentMapper talentMapper;

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
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) {
        List<UserGrade> userGradeList = gradeMapper.queryByUser(id);
        for (UserGrade grade:userGradeList){
            passageMapper.deleteByGradeId(grade.getGradeId());
            patentMapper.deleteByGradeId(grade.getGradeId());
            prizeMapper.deleteByGradeId(grade.getGradeId());
            proMapper.deleteByGradeId(grade.getGradeId());
            resultMapper.deleteByGradeId(grade.getGradeId());
            stuMapper.deleteByGradeId(grade.getGradeId());
            talentMapper.deleteByGradeId(grade.getGradeId());
            gradeMapper.deleteById(grade.getGradeId());
        }
        return userMapper.deleteById(id);
    }

    @Override
    public User doLogin(String tel, String passwd) {
        User user = userMapper.queryByTel(tel);
        if(user!=null){
            if(user.getPasswd().equals(passwd)){
                return user;
            }
        }
        return null;
    }

    @Override
    public int changePasswd(String id, String passwd) {
        return userMapper.changePasswd(id,passwd);
    }

    @Override
    public List<UserParam> queryUserParam(String tel) {
        return userMapper.queryUserParam(tel);
    }

    @Override
    public User queryByIdCard(String idcard) {
        return userMapper.queryByIdCard(idcard);
    }
}
