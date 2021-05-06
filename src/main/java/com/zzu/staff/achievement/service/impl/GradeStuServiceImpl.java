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
import com.zzu.staff.achievement.service.IndexNationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private IndexNationMapper nationMapper;

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
        return mapper.queryByGradeId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) throws Exception {
        GradeStu stu = mapper.queryById(id);
        UserGrade userGrade = userGradeMapper.queryById(stu.getGradeId());
        userGrade.setStu(userGrade.getStu()-stu.getStuGrade());
        float sum = userGrade.getSum()-stu.getStuGrade();
        userGrade.setSum(sum);

        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }

        if(userGradeMapper.update(userGrade)==1) {
            return mapper.deleteById(id);
        }else{
            throw new Exception("更新总分出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradeStu stu) throws Exception {
        GradeStu origin = mapper.queryById(stu.getStuGradeId());
        float g = getGrade(stu);
        stu.setStuGrade(g);
        if(mapper.update(stu)==1){
            UserGrade grade = userGradeMapper.queryById(stu.getGradeId());
            grade.setStu(grade.getStu()+g-origin.getStuGrade());

            float sum = grade.getSum()+g-origin.getStuGrade();
            grade.setSum(sum);

            User user = userMapper.queryById(grade.getUId());
            IndexNation nation = nationMapper.queryById(user.getNation());
            if(sum>nation.getNationLevel()){
                grade.setIndexSum((float)nation.getNationCode());
            }else{
                grade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
            }

            if(userGradeMapper.update(grade)==1){
                return 1;
            }else {
                throw new Exception( "userGrade更新失败！");
            }
        }else{
            throw new Exception("数据添加失败");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertS(GradeStu stu) throws Exception{
        float g = getGrade(stu);
        stu.setStuGrade(g);
        if(mapper.insert(stu)==1){
            UserGrade grade = userGradeMapper.queryById(stu.getGradeId());
            grade.setStu(grade.getStu()+g);
            float sum  = grade.getSum()+g;
            grade.setSum(sum);

            User user = userMapper.queryById(grade.getUId());
            IndexNation nation = nationMapper.queryById(user.getNation());
            if(sum>nation.getNationLevel()){
                grade.setIndexSum((float)nation.getNationCode());
            }else{
                grade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
            }

            if(userGradeMapper.update(grade)==1){
                return 1;
            }else {
                throw new Exception("userGrade更新失败！");
            }
        }else{
            throw new Exception("数据添加失败");
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
