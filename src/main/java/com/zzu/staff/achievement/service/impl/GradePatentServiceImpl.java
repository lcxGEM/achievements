package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.GradePatent;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.mapper.GradePatentMapper;
import com.zzu.staff.achievement.mapper.UserGradeMapper;
import com.zzu.staff.achievement.service.IGradePatentService;
import com.zzu.staff.achievement.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专利情况，无要求
 *
 */
@Service
public class GradePatentServiceImpl implements IGradePatentService {

    @Value("${patent.A}")
    private Integer A;

    @Value("${patent.B}")
    private Integer B;

    @Autowired
    private GradePatentMapper mapper;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private IUserGradeService gradeService;


    @Override
    public GradePatent insert(GradePatent patent) {
        int grade = getGrade(patent);
        patent.setPatentGrade(grade);
        System.out.println("--->专利："+patent.toString());
        if(mapper.insert(patent)==1){
            return patent;
        }else {
            return null;
        }
    }

    @Override
    public List<GradePatent> queryByGradeId(long id) {
        return mapper.queryByGradeId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) throws Exception {
        GradePatent gradePatent = mapper.queryById(id);
        UserGrade userGrade = gradeMapper.queryById(gradePatent.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        userGrade.setPatent(userGrade.getPatent()-gradePatent.getPatentGrade());
        float sum = userGrade.getSum()-gradePatent.getPatentGrade();
        userGrade.setSum(sum);

        if(gradeService.update(userGrade)==1){
            if(mapper.deleteById(id)==1){
                return 1;
            }else{
                throw new Exception("patent删除出错！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertA(GradePatent patent) throws Exception {
        int grade = getGrade(patent);
        patent.setPatentGrade(grade);
        UserGrade userGrade = gradeMapper.queryById(patent.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        userGrade.setPatent(userGrade.getPatent()+patent.getPatentGrade());
        float sum = userGrade.getSum()+patent.getPatentGrade();
        userGrade.setSum(sum);

        if(gradeService.update(userGrade)==1){
            if(mapper.insert(patent)==1){
                return 1;
            }else{
                throw new Exception("patent添加出错！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradePatent patent) throws Exception {
        GradePatent origin = mapper.queryById(patent.getPatentId());
        UserGrade userGrade = gradeMapper.queryById(patent.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        int grade = getGrade(patent);
        patent.setPatentGrade(grade);
        System.out.println("--->专利："+patent.toString());
        if(origin.getPatentGrade()!=grade){
            userGrade.setPatent(userGrade.getPatent()-origin.getPatentGrade()+grade);
            float sum = userGrade.getSum()-origin.getPatentGrade()+grade;
            userGrade.setSum(sum);

            if(gradeService.update(userGrade)!=1){
                throw new Exception("grade更新出错！");
            }
        }
        if(mapper.update(patent)==1) {
            return 1;
        }else {
            throw new Exception("patent更新出错！");
        }
    }

    private int getGrade(GradePatent patent){
        if(patent.getIsInventor()){
            if(patent.getNature()==1){
                return A;
            }else if(patent.getNature()==2){
                return B;
            }else {
                return 0;
            }
        }else{
            return 0;
        }
    }
}
