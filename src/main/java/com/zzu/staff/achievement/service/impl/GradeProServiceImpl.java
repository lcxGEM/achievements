package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.*;
import com.zzu.staff.achievement.service.IGradeProService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        GradePro pro = mapper.queryById(id);
        UserGrade userGrade = gradeMapper.queryById(pro.getGradeId());
        userGrade.setProgram(userGrade.getProgram()-pro.getProGrade());
        float sum = userGrade.getSum()-pro.getProGrade();
        userGrade.setSum(sum);

        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }

        if(gradeMapper.update(userGrade)==1) {
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
        float grade = getGrade(pro);
        pro.setProGrade(grade);
        System.out.println("--->科研："+pro.toString());
        UserGrade userGrade = gradeMapper.queryById(pro.getGradeId());
        userGrade.setProgram(userGrade.getProgram()+pro.getProGrade());
        float sum = userGrade.getSum()+pro.getProGrade();
        userGrade.setSum(sum);

        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }

        if(gradeMapper.update(userGrade)==1) {
            if(mapper.insert(pro)==1){
                return 1;
            }else {
                throw new Exception("pro添加出错！");
            }
        }else {
            throw  new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradePro pro) throws Exception {
        GradePro origin  = mapper.queryById(pro.getProgramId());
        float grade = getGrade(pro);
        pro.setProGrade(grade);
        System.out.println("--->科研："+pro.toString());

        UserGrade userGrade = gradeMapper.queryById(pro.getGradeId());
        if(origin.getProGrade()!=grade) {
            userGrade.setProgram(userGrade.getProgram() + grade - origin.getProGrade());
            float sum = userGrade.getSum() + grade - origin.getProGrade();
            userGrade.setSum(sum);

            User user = userMapper.queryById(userGrade.getUId());
            IndexNation nation = nationMapper.queryById(user.getNation());
            if(sum>nation.getNationLevel()){
                userGrade.setIndexSum((float)nation.getNationCode());
            }else{
                userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
            }

            if(gradeMapper.update(userGrade)!=1){
                throw new Exception("grade更新出错");
            }
        }
        if(mapper.update(pro)==1){
            return 1;
        }else{
            throw new Exception("pass更新出错！");
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
