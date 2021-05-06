package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.*;
import com.zzu.staff.achievement.service.IGradePrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GradePrizeServiceImpl implements IGradePrizeService {

    @Autowired
    private GradePrizeMapper mapper;

    @Autowired
    private IndexPrizeMapper mapperIndex;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IndexNationMapper nationMapper;

    @Override
    public GradePrize insert(GradePrize prize) {
        float grade = getGrade(prize);
        prize.setPrizeGrade(grade);
        System.out.println("--->获奖情况："+prize.toString());
        if(mapper.insert(prize)==1){
            return prize;
        }else {
            return null;
        }
    }

    @Override
    public List<GradePrizeParam> queryByGradeId(long id) {
        return mapper.queryByGradeId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) throws Exception {
        GradePrize gradePrize = mapper.queryById(id);
        UserGrade userGrade = gradeMapper.queryById(gradePrize.getGradeId());
        userGrade.setPrize(userGrade.getPrize()-gradePrize.getPrizeGrade());
        float sum = userGrade.getSum()-gradePrize.getPrizeGrade();
        userGrade.setSum(sum);
        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }
        if(gradeMapper.update(userGrade)==1){
            if(mapper.deleteById(id)==1){
                return 1;
            }else{
                throw new Exception("prize删除出错！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertA(GradePrize prize) throws Exception {
        float grade = getGrade(prize);
        prize.setPrizeGrade(grade);
        System.out.println("--->获奖情况："+prize.toString());
        UserGrade userGrade = gradeMapper.queryById(prize.getGradeId());
        userGrade.setPrize(userGrade.getPrize()+prize.getPrizeGrade());
        float sum = userGrade.getSum()+prize.getPrizeGrade();
        userGrade.setSum(sum);

        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }

        if(gradeMapper.update(userGrade)==1){
            if(mapper.insert(prize)==1){
                return 1;
            }else{
                throw new Exception("prize添加出错！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradePrize prize) throws Exception {
        GradePrize origin = mapper.queryById(prize.getPrizeId());
        UserGrade userGrade = gradeMapper.queryById(prize.getGradeId());
        float grade = getGrade(prize);
        prize.setPrizeGrade(grade);
        System.out.println("--->获奖情况："+prize.toString());
        if(grade!=origin.getPrizeGrade()){
            float sum = userGrade.getSum()-origin.getPrizeGrade()+grade;
            userGrade.setSum(sum);
            userGrade.setPrize(userGrade.getPrize()-origin.getPrizeGrade()+grade);
            User user = userMapper.queryById(userGrade.getUId());
            IndexNation nation = nationMapper.queryById(user.getNation());
            if(sum>nation.getNationLevel()){
                userGrade.setIndexSum((float)nation.getNationCode());
            }else{
                userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
            }

            if(gradeMapper.update(userGrade)!=1){
                throw new Exception("grade更新出错！");
            }
        }
        if(mapper.update(prize)==1) {
            return 1;
        }else {
            throw new Exception("prize更新出错！");
        }
    }

    private float getGrade(GradePrize prize){
        IndexPrizeLevel level = mapperIndex.queryLevelByTypeLevel(prize.getPrizeLevel());
        if (level!=null){
            float grade = level.getPrizeLevelGrade();
            if(prize.getPrizeSchoolOrder()>0){
                grade = grade/prize.getPrizeSchoolOrder();
            }
            int a = prize.getPrizeAuthorOrder();
            for (int i=0;i<a-1;i++){
                grade /=2;
            }
            return grade;//----------------------
        }else {
            return 0;
        }
    }
}
