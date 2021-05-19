package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.GradeStu;
import com.zzu.staff.achievement.entity.IndexNation;
import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.mapper.GradeStuMapper;
import com.zzu.staff.achievement.mapper.IndexNationMapper;
import com.zzu.staff.achievement.mapper.UserGradeMapper;
import com.zzu.staff.achievement.mapper.UserMapper;
import com.zzu.staff.achievement.service.IGradeStuService;
import com.zzu.staff.achievement.service.IUserGradeService;
import com.zzu.staff.achievement.service.IndexNationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 指导博士，博士后
 * 只有求是学人、求是学者 9、10才能操作
 */
@Service
public class GradeStuServiceImpl implements IGradeStuService{

    @Value("${stu.A}")
    private Integer A;

    @Value("${stu.B}")
    private Integer B;

    @Value("${stu.K1}")
    private Float K1;//基础系数

    @Value("${stu.K2}")
    private Float K2;//博新计划

    @Value("${stu.K3}")
    private Float K3;//特别资助

    @Autowired
    private GradeStuMapper mapper;

    @Autowired
    private UserGradeMapper userGradeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserGradeService gradeService;

    @Override
    public GradeStu insert(GradeStu stu) {
        float grade = getGrade(stu);
        stu.setStuGrade(grade);
        System.out.println("--->教学指导："+stu.toString());
        if(mapper.insert(stu)==1){
            return stu;
        }else{
            return null;
        }
    }

    @Override
    public List<GradeStu> queryByGradeId(long id) {
        UserGrade userGrade = userGradeMapper.queryById(id);//获取grade
        User user = userMapper.queryById(userGrade.getUId());//获取user
        if (user==null){
            return null;
        }
        if(user.getNation()==9||user.getNation()==10){ //判断是否为求是学人、求是学者
            return mapper.queryByGradeId(id);
        }
        return new ArrayList<>();
    }
    //1.验证是否为求是学人
    // 删除数据
    // 计算总分
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) throws Exception {
        //验证身份
        GradeStu stu = mapper.queryById(id);//要删除的数据
        UserGrade userGrade = userGradeMapper.queryById(stu.getGradeId());//获取grade
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());//获取user
        if (user==null){
            return -1;
        }
        if(user.getNation()==9||user.getNation()==10){ //判断是否为求是学人、求是学者
            //计算分数
            userGrade.setStu(userGrade.getStu()-stu.getStuGrade());//减去单个数据
            float sum = userGrade.getSum()-stu.getStuGrade();
            userGrade.setSum(sum);//设置总分
            //用service层的update，直接计算总分
            if(gradeService.update(userGrade)==1) {
                return mapper.deleteById(id);
            }else{
                throw new Exception("更新总分出错！");
            }
        }else{//不是没有操作
            return -2;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradeStu stu) throws Exception {
        //验证身份
        GradeStu origin = mapper.queryById(stu.getStuGradeId());
        UserGrade grade = userGradeMapper.queryById(stu.getGradeId());//获取
        if(grade.getStatus()==2||grade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }

        User user = userMapper.queryById(grade.getUId());
        if (user==null){
            return -1;
        }
        if(user.getNation()==9||user.getNation()==10) { //判断是否为求是学人、求是学者
            float g = getGrade(stu);
            stu.setStuGrade(g);
            System.out.println("@@@@@@@@"+stu.toString());
            if(stu.getEducation()==1){//博士生的话保证博新计划和博特计划为否
                stu.setBoxin(false);
                stu.setBote(false);
            }
            if (mapper.update(stu) == 1) {
                grade.setStu(grade.getStu() + g - origin.getStuGrade());
                float sum = grade.getSum() + g - origin.getStuGrade();
                grade.setSum(sum);
                if (gradeService.update(grade) == 1) {
                    return 1;
                } else {
                    throw new Exception("userGrade更新失败！");
                }
            } else {
                throw new Exception("数据添加失败");
            }
        }else{
            return -2;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertS(GradeStu stu) throws Exception{
        //验证身份
        UserGrade grade = userGradeMapper.queryById(stu.getGradeId());
        if(grade.getStatus()==2||grade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(grade.getUId());
        if(user==null){
            return -1;
        }
        if(user.getNation()==9||user.getNation()==10) { //判断是否为求是学人、求是学者
            float g = getGrade(stu);
            stu.setStuGrade(g);
            if(stu.getEducation()==1){//博士生的话保证博新计划和博特计划为否
                stu.setBoxin(false);
                stu.setBote(false);
            }
            if (mapper.insert(stu) == 1) {

                grade.setStu(grade.getStu() + g);
                float sum = grade.getSum() + g;
                grade.setSum(sum);
                if (gradeService.update(grade) == 1) {
                    return 1;
                } else {
                    throw new Exception("userGrade更新失败！");
                }
            } else {
                throw new Exception("数据添加失败");
            }
        }else{
            return -2;//不需要操作此项
        }
    }

    private float getGrade(GradeStu stu){
        //
        if(stu.getEducation()==1){//博士生 使用A
            return A;
        }else{//博士后 使用B
            if(stu.isBoxin()){
                return B*K2;
            }else if(stu.isBote()){
                return B*K3;
            }else{
                return B*K1;
            }
        }
    }
}
