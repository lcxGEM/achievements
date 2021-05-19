package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.*;
import com.zzu.staff.achievement.service.IGradeTalentService;
import com.zzu.staff.achievement.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 人才项目
 * 只有求是学人、求是学者9、10才能操作
 */
@Service
public class GradeTalentServiceImpl implements IGradeTalentService {

    @Autowired
    private GradeTalentMapper mapper;

    @Autowired
    private IndexTalentMapper talentMapper;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserGradeService gradeService;

    @Override
    public GradeTalent insert(GradeTalent talent) {
        float grade = getGrade(talent);
        talent.setTalentGrade(grade);
        System.out.println("--->人才项目："+talent.toString());
        if(mapper.insert(talent)==1){
            return talent;
        }else {
            return null;
        }
    }

    @Override
    public GradeTalent queryByGradeId(long id) {
        UserGrade userGrade = gradeMapper.queryById(id);
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return null;
        }
        if(user.getNation()==9||user.getNation()==10){
            GradeTalent talent = mapper.queryById(id);
            if(talent!=null){
                return talent;
            }else{
                talent = new GradeTalent(id,0,null,0f);
                mapper.insert(talent);
                return talent;
            }
        }
        GradeTalent talent = new GradeTalent();
        talent.setGradeId(-1l);
        return talent;
    }

    // 验证是否符合要求、
    // 计算更新值、
    // 更新数据
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradeTalent talent) throws Exception {
        UserGrade userGrade = gradeMapper.queryById(talent.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return -1;
        }
        if(user.getNation()==9||user.getNation()==10){
            float grade = getGrade(talent);//计算新分数
            talent.setTalentGrade(grade);
            userGrade.setSum(userGrade.getSum()-userGrade.getTalent()+grade);//计算总分
            userGrade.setTalent(grade);//计算人才项目分
            if(gradeService.update(userGrade)==1){
                if(mapper.update(talent)==1){
                    return 1;
                }else{
                    throw  new Exception("updateTalent出错！");
                }
            }else{
                throw new Exception("grade更新出错！");
            }
        }else{
            return -2;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertA(GradeTalent talent) throws Exception  {
        UserGrade userGrade = gradeMapper.queryById(talent.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return -1;
        }
        if(user.getNation()==9||user.getNation()==10){
            float grade = getGrade(talent);//计算新分数
            talent.setTalentGrade(grade);
            userGrade.setSum(userGrade.getSum()+grade);//计算总分
            userGrade.setTalent(grade);//计算人才项目分
            if(gradeService.update(userGrade)==1){
                if(mapper.insert(talent)==1){
                    return 1;
                }else{
                    throw  new Exception("updateTalent出错！");
                }
            }else{
                throw new Exception("grade更新出错！");
            }
        }else{
            return -2;
        }
    }

    private float getGrade(GradeTalent talent){
        IndexTalent talent1 = talentMapper.queryById(talent.getTalentId());
        if(talent1!=null){
            return talent1.getGrade();
        }else{
            return 0;
        }
    }
}
