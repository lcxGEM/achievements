package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.GradeResult;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.mapper.GradeResultMapper;
import com.zzu.staff.achievement.mapper.UserGradeMapper;
import com.zzu.staff.achievement.service.IGradeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GradeResultServiceImpl implements IGradeResultService {

    @Autowired
    private GradeResultMapper mapper;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Override
    public GradeResult insert(GradeResult result) {
        result.setResultGrade(getGrade(result));
        System.out.println("--->成果转化："+result.toString());
        if(mapper.insert(result)==1){
            return result;
        }else {
            return null;
        }
    }

    @Override
    public List<GradeResult> queryByGradeId(Long id) {
        return mapper.queryByGradeId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(Long id) throws Exception {
        GradeResult result = mapper.queryById(id);
        UserGrade userGrade = gradeMapper.queryById(result.getGradeId());
        userGrade.setResult(userGrade.getResult()-result.getResultGrade());
        userGrade.setSum(userGrade.getSum()-result.getResultGrade());
        if(gradeMapper.update(userGrade)==1){
            if(mapper.deleteById(id)==1){
                return 1;
            }else {
                throw new Exception("result删除失败！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertA(GradeResult result) throws Exception {
        result.setResultGrade(getGrade(result));
        System.out.println("--->成果转化："+result.toString());
        UserGrade userGrade = gradeMapper.queryById(result.getGradeId());
        userGrade.setResult(userGrade.getResult()+result.getResultGrade());
        userGrade.setSum(userGrade.getSum()+result.getResultGrade());
        if(gradeMapper.update(userGrade)==1){
            if(mapper.insert(result)==1){
                return 1;
            }else {
                throw new Exception("result添加失败！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradeResult result) throws Exception {
        float grade = getGrade(result);
        result.setResultGrade(grade);
        System.out.println("--->成果转化："+result.toString());
        GradeResult origin = mapper.queryById(result.getResultId());
        if(origin.getResultGrade()!=grade){
            UserGrade userGrade = gradeMapper.queryById(result.getGradeId());
            userGrade.setSum(userGrade.getSum()-origin.getResultGrade()+grade);
            userGrade.setResult(userGrade.getResult()-origin.getResultGrade()+grade);
            if(gradeMapper.update(userGrade)!=1){
                throw new Exception("grade更新出错！");
            }
        }
        if(mapper.update(result)==1) {
            return 1;
        }else {
            throw new Exception("result更新出错！");
        }
    }

    private float getGrade(GradeResult result){
        return (result.getResultNum()*1.8f);
    }
}
