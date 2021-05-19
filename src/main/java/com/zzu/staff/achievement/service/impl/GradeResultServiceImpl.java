package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.GradeResult;
import com.zzu.staff.achievement.entity.IndexNation;
import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.mapper.GradeResultMapper;
import com.zzu.staff.achievement.mapper.IndexNationMapper;
import com.zzu.staff.achievement.mapper.UserGradeMapper;
import com.zzu.staff.achievement.mapper.UserMapper;
import com.zzu.staff.achievement.service.IGradeResultService;
import com.zzu.staff.achievement.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 成果转化
 * 只有求是学人、求是学者 9、10才能操作
 */
@Service
public class GradeResultServiceImpl implements IGradeResultService {

    @Autowired
    private GradeResultMapper mapper;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IndexNationMapper nationMapper;

    @Autowired
    private IUserGradeService gradeService;

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
        UserGrade userGrade = gradeMapper.queryById(id);
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return null;
        }
        if(user.getNation()==9||user.getNation()==10){
            return mapper.queryByGradeId(id);
        }else{
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(Long id) throws Exception {
        GradeResult result = mapper.queryById(id);//查询原来的GradeResult
        UserGrade userGrade = gradeMapper.queryById(result.getGradeId());//查询业绩总分
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());//查询用户
        if(user.getNation()==9||user.getNation()==10){
            userGrade.setResult(userGrade.getResult()-result.getResultGrade());
            float sum = userGrade.getSum()-result.getResultGrade();
            userGrade.setSum(sum);
            if(gradeService.update(userGrade)==1){
                if(mapper.deleteById(id)==1){
                    return 1;
                }else {
                    throw new Exception("result删除失败！");
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
    public int insertA(GradeResult result) throws Exception {
        UserGrade userGrade = gradeMapper.queryById(result.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());
        if(user.getNation()==9||user.getNation()==10){
            result.setResultGrade(getGrade(result)); //设置成果转化分
            userGrade.setResult(userGrade.getResult()+result.getResultGrade()); //计算总分中的成果转化分
            float sum = userGrade.getSum()+result.getResultGrade();//计算总分
            userGrade.setSum(sum);
            if(gradeService.update(userGrade)==1){
                if(mapper.insert(result)==1){
                    return 1;
                }else {
                    throw new Exception("result添加失败！");
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
    public int update(GradeResult result) throws Exception {
        UserGrade userGrade = gradeMapper.queryById(result.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());
        if(user.getNation()==9||user.getNation()==10) {
            float grade = getGrade(result);
            result.setResultGrade(grade);
            GradeResult origin = mapper.queryById(result.getResultId());
            if (origin.getResultGrade() != grade) {
                float sum = userGrade.getSum() - origin.getResultGrade() + grade;
                userGrade.setSum(sum);
                userGrade.setResult(userGrade.getResult() - origin.getResultGrade() + grade);
                if (gradeService.update(userGrade) != 1) {
                    throw new Exception("grade更新出错！");
                }
            }
            if (mapper.update(result) == 1) {
                return 1;
            } else {
                throw new Exception("result更新出错！");
            }
        }else{
            return -2;
        }
    }

    private float getGrade(GradeResult result){
        return (result.getResultNum()*1.8f);
    }
}
