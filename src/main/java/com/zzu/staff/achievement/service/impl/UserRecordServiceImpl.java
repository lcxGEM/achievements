package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.SchoolMapper;
import com.zzu.staff.achievement.mapper.SchoolTypeMapper;
import com.zzu.staff.achievement.mapper.UserMapper;
import com.zzu.staff.achievement.mapper.UserRecordMapper;
import com.zzu.staff.achievement.service.IUserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecordServiceImpl implements IUserRecordService {

    @Autowired
    private UserRecordMapper staffMapper;

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private SchoolTypeMapper schoolTypeMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserRecordVo> queryAllVo(String sName, int teacherType, int gsType, int msType, int dsType, int depart) {
        String trim = sName.trim();
        return staffMapper.searchAllVo(trim,teacherType,gsType,msType,dsType,depart);
    }

    @Override
    public UserRecordVo queryById(long id) {
        return staffMapper.queryById(id);
    }
    //新建学历，教师填写用，需要修改
    @Override
    public int insert(UserRecord staff) {
        UserRecord record = staffMapper.queryRecordByUsId(staff.getUsId());
        if(record==null){
            staff = setCompositeIndexAndEvaluation(staff);
            return staffMapper.insert(staff);
        }else{
            staff.setRecordId(record.getRecordId());
            staff = setCompositeIndexAndEvaluation(staff);
            return staffMapper.update(staff);
        }
    }

    @Override
    public int deleteById(long id) {
        return staffMapper.deleteById(id);
    }

    @Override
    public int update(UserRecord staff) {
        staff = setCompositeIndexAndEvaluation(staff);
        return staffMapper.update(staff);
    }

    @Override
    public int updateAll(int stId) {
        int a=0;
        if(stId!=0){
            return -1;
        }else{
            List<UserRecord> staffList = staffMapper.queryAll();
            for (UserRecord staff:staffList){
                staff = setCompositeIndexAndEvaluation(staff);
                a+=staffMapper.update(staff);
            }
            return a;
        }
    }

    @Override
    public UserRecord queryRecordByUsId(long id) {
        return staffMapper.queryRecordByUsId(id);
    }


    private UserRecord setCompositeIndexAndEvaluation(UserRecord staff){
        float masterIndex = 1f;   //硕士基准值
        float underIndex = 1f;
        float doctorIndex = 1f;
        float basicIndex = 1f;
        User user = userMapper.queryById(staff.getUsId());
        if(user.getNation()==1){//原值
            if(staff.getMasterSchool()!=0){
                School masterSchool = schoolMapper.queryById(staff.getMasterSchool());//硕士学校
                masterIndex = schoolTypeMapper.queryById(masterSchool.getSchoolType()).getMasterIndex(); //硕士基准值
            }
            if(staff.getUndergraduateSchool()!=0){
                School underSchool = schoolMapper.queryById(staff.getUndergraduateSchool());//学士
                underIndex = schoolTypeMapper.queryById(underSchool.getSchoolType()).getUndergraduateIndex(); //学士基准值
            }
            if(staff.getDoctorSchool()!=0){
                School doctorSchool = schoolMapper.queryById(staff.getDoctorSchool());//博士
                doctorIndex = schoolTypeMapper.queryById(doctorSchool.getSchoolType()).getDoctorIndex(); //博士基准值
            }
            //queryByName("基准值")
            SchoolType schoolType=schoolTypeMapper.queryByName("基准值");
            basicIndex = schoolType.getSumIndex(); //基准值
        }else{//新值
            if(staff.getMasterSchool()!=0){
                School masterSchool = schoolMapper.queryById(staff.getMasterSchool());//硕士学校
                masterIndex = schoolTypeMapper.queryById(masterSchool.getSchoolType()).getAmasterIndex(); //硕士基准值
            }
            if(staff.getUndergraduateSchool()!=0){
                School underSchool = schoolMapper.queryById(staff.getUndergraduateSchool());//学士
                underIndex = schoolTypeMapper.queryById(underSchool.getSchoolType()).getAundergraduateIndex(); //学士基准值
            }
            if(staff.getDoctorSchool()!=0){
                School doctorSchool = schoolMapper.queryById(staff.getDoctorSchool());//博士
                doctorIndex = schoolTypeMapper.queryById(doctorSchool.getSchoolType()).getAdoctorIndex(); //博士基准值
            }
            //queryByName("基准值")
            SchoolType schoolType=schoolTypeMapper.queryById(40);
            basicIndex = schoolType.getAsumIndex(); //基准值
        }
        Float compositeIndex = 75f*underIndex*masterIndex*doctorIndex/basicIndex;
        System.out.println("------------------------------------>75*"+underIndex+"*"+masterIndex+"*"+doctorIndex+"/"+basicIndex+"="+compositeIndex);
        if(compositeIndex>100){
            compositeIndex = 100f;
        }
        staff.setCompositeIndex(compositeIndex);
        if(compositeIndex>=80){
            staff.setEvaluation("优");
        }else if(compositeIndex>=70){
            staff.setEvaluation(("良"));
        }else if(compositeIndex>=60){
            staff.setEvaluation("中");
        }else {
            staff.setEvaluation("差");
        }
        return staff;
    }
}
