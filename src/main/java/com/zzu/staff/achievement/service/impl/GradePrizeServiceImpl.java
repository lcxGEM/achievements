package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.*;
import com.zzu.staff.achievement.service.IGradePrizeService;
import com.zzu.staff.achievement.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 获奖情况，无限制
 */
@Service
public class GradePrizeServiceImpl implements IGradePrizeService {

    @Autowired
    private GradePrizeMapper mapper;

    @Autowired
    private IndexPrizeMapper mapperIndex;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private IUserGradeService gradeService;

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
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        userGrade.setPrize(userGrade.getPrize()-gradePrize.getPrizeGrade());//设置获奖积分
        float sum = userGrade.getSum()-gradePrize.getPrizeGrade();//修改总积分
        userGrade.setSum(sum);
        //更新积分信息，并删除该项
        if(gradeService.update(userGrade)==1){
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
        UserGrade userGrade = gradeMapper.queryById(prize.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        userGrade.setPrize(userGrade.getPrize()+prize.getPrizeGrade());//添加获奖积分
        float sum = userGrade.getSum()+prize.getPrizeGrade();//更新总积分
        userGrade.setSum(sum);
        //更新积分并添加该项
        if(gradeService.update(userGrade)==1){
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
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        float grade = getGrade(prize);
        prize.setPrizeGrade(grade);
        if(grade!=origin.getPrizeGrade()){//判断更新前后的积分有没有变化
            //有变化先更新积分
            float sum = userGrade.getSum()-origin.getPrizeGrade()+grade;
            userGrade.setSum(sum);
            userGrade.setPrize(userGrade.getPrize()-origin.getPrizeGrade()+grade);

            if(gradeService.update(userGrade)!=1){
                throw new Exception("grade更新出错！");
            }
        }
        //无变化直接更新该项
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
