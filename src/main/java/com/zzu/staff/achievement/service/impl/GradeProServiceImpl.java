package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.*;
import com.zzu.staff.achievement.service.IGradeProService;
import com.zzu.staff.achievement.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 科研项目
 * 求是学人 9 计算的时候不计算中国博士后科学基金特别资助项目
 */
@Service
public class GradeProServiceImpl implements IGradeProService {

    @Autowired
    private GradeProMapper mapper;

    @Autowired
    private IndexProMapper mapperIndex;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IndexNationMapper nationMapper;

    @Autowired
    private IUserGradeService gradeService;

    @Override
    public GradePro insert(GradePro pro) {
        float grade = getGrade(pro);
        pro.setProGrade(grade);
        System.out.println("--->科研："+pro.toString());
        if(mapper.insert(pro)==1){
            return pro;
        }else {
            return null;
        }
    }

    @Override
    public List<GradeProParam> queryByGradeId(long id) {
        return mapper.queryByGradeId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) throws Exception {
        GradePro pro = mapper.queryById(id);//查询科研项目
        UserGrade userGrade = gradeMapper.queryById(pro.getGradeId());//查询业绩
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        userGrade.setProgram(userGrade.getProgram()-pro.getProGrade());//计算科研项目分
        float sum = userGrade.getSum()-pro.getProGrade();//计算总分
        userGrade.setSum(sum);

        if(gradeService.update(userGrade)==1) {
            if(mapper.deleteById(id)==1){
                return 1;
            }else {
                throw new Exception("pro删除出错！");
            }
        }else {
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertA(GradePro pro) throws Exception {
        UserGrade userGrade = gradeMapper.queryById(pro.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return -1;
        }
        if(user.getNation()==9&&pro.getProLevel()==26){//求是学人
            return -2;
        }else{
            float grade = getGrade(pro);
            pro.setProGrade(grade);
            userGrade.setProgram(userGrade.getProgram()+pro.getProGrade());
            float sum = userGrade.getSum()+pro.getProGrade();
            userGrade.setSum(sum);

            if(gradeService.update(userGrade)==1) {
                if(mapper.insert(pro)==1){
                    return 1;
                }else {
                    throw new Exception("pro添加出错！");
                }
            }else {
                throw  new Exception("grade更新出错！");
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradePro pro) throws Exception {
        UserGrade userGrade = gradeMapper.queryById(pro.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return -1;
        }
        if(user.getNation()==9&&pro.getProLevel()==26){//求是学人
            return -2;
        }else {
            GradePro origin = mapper.queryById(pro.getProgramId());
            float grade = getGrade(pro);
            pro.setProGrade(grade);
            if (origin.getProGrade() != grade) {
                userGrade.setProgram(userGrade.getProgram() + grade - origin.getProGrade());
                float sum = userGrade.getSum() + grade - origin.getProGrade();
                userGrade.setSum(sum);
                if (gradeService.update(userGrade) != 1) {
                    throw new Exception("grade更新出错");
                }
            }
            if (mapper.update(pro) == 1) {
                return 1;
            } else {
                throw new Exception("pass更新出错！");
            }
        }
    }

    private float getGrade(GradePro pro){
        IndexPro index = mapperIndex.queryById(pro.getProLevel());
        if(index!=null){
            if(pro.getHost()){
                return index.getProLevelGrade();
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }
}
