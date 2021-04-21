package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.GradeTalent;
import com.zzu.staff.achievement.entity.IndexTalent;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.mapper.GradeTalentMapper;
import com.zzu.staff.achievement.mapper.IndexTalentMapper;
import com.zzu.staff.achievement.mapper.UserGradeMapper;
import com.zzu.staff.achievement.service.IGradeTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GradeTalentServiceImpl implements IGradeTalentService {

    @Autowired
    private GradeTalentMapper mapper;

    @Autowired
    private IndexTalentMapper talentMapper;

    @Autowired
    private UserGradeMapper gradeMapper;

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
        return mapper.queryById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradeTalent talent) throws Exception {
        float grade = getGrade(talent);
        talent.setTalentGrade(grade);
        UserGrade userGrade = gradeMapper.queryById(talent.getGradeId());
        userGrade.setSum(userGrade.getSum()-userGrade.getTalent()+grade);
        userGrade.setTalent(grade);
        if(gradeMapper.update(userGrade)==1){
            if(mapper.update(talent)==1){
                return 1;
            }else{
                throw  new Exception("updateTalent出错！");
            }
        }else{
            throw new Exception("grade更新出错！");
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
