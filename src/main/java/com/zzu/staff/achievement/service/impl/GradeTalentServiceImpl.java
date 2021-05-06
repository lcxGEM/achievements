package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.*;
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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IndexNationMapper nationMapper;

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

        float sum = userGrade.getSum()-userGrade.getTalent()+grade;
        userGrade.setSum(userGrade.getSum()-userGrade.getTalent()+grade);


        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }

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
