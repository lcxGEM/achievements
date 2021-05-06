package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.GradePassage;
import com.zzu.staff.achievement.entity.IndexNation;
import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.mapper.GradePassageMapper;
import com.zzu.staff.achievement.mapper.IndexNationMapper;
import com.zzu.staff.achievement.mapper.UserGradeMapper;
import com.zzu.staff.achievement.mapper.UserMapper;
import com.zzu.staff.achievement.service.IGradePassageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GradePassageServiceImpl implements IGradePassageService {

    @Value("${passage.DTOP}")
    private int DTOP;

    @Value("${passage.TOP}")
    private int TOP;

    @Value("${passage.A}")
    private int A;

    @Value("${passage.B}")
    private int B;

    @Autowired
    private GradePassageMapper mapper;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IndexNationMapper nationMapper;

    @Override
    public GradePassage insert(GradePassage passage) {
        float grade = getGrade(passage);
        passage.setPassageGrade(grade);
        System.out.println("--->论文："+passage.toString());
        if(mapper.insert(passage)==1){
            return passage;
        }else{
            return null;
        }
    }

    @Override
    public List<GradePassage> queryByGradeId(long id) {
        return mapper.queryByGradeId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) throws Exception {
        GradePassage passage =  mapper.queryById(id);
        List<GradePassage> passageList = mapper.queryByGradeId(passage.getGradeId());//获取所有论文
        UserGrade userGrade = gradeMapper.queryById(passage.getGradeId());
        //计算论文总分
        float sumPass = calculateSum(passageList,id);
        //总分计算完毕，更新userGrade
        float sum = userGrade.getSum()-userGrade.getPassage()+sumPass;
        userGrade.setSum(sum);

        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }
        userGrade.setPassage(sumPass);
        if(gradeMapper.update(userGrade)==1) {
            if(mapper.deleteById(id)==1){
                return 1;
            }else{
                throw new Exception("passage删除出错！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int insertA(GradePassage passage) throws Exception {
        float grade = getGrade(passage);
        passage.setPassageGrade(grade);
        List<GradePassage> passageList = mapper.queryByGradeId(passage.getGradeId());//获取所有论文
        UserGrade userGrade = gradeMapper.queryById(passage.getGradeId());
        passageList.add(passage);
        float sumPass = calculateSum(passageList,0l);
        //总分计算完毕，更新userGrade
        float sum  = userGrade.getSum()-userGrade.getPassage()+sumPass;
        userGrade.setSum(sum);

        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }

        userGrade.setPassage(sumPass);
        if(gradeMapper.update(userGrade)==1) {
            if(mapper.insert(passage)==1){
                return 1;
            }else {
                throw new Exception("passage添加出错！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(GradePassage passage) throws Exception {
        float grade = getGrade(passage);
        passage.setPassageGrade(grade);
        List<GradePassage> passageList = mapper.queryByGradeId(passage.getGradeId());//获取所有论文
        UserGrade userGrade = gradeMapper.queryById(passage.getGradeId());
        passageList.remove(passage);
        passageList.add(passage);
        float sumPass = calculateSum(passageList,0l);
        float sum = userGrade.getSum()-userGrade.getPassage()+sumPass;
        userGrade.setSum(sum);
        userGrade.setPassage(sumPass);

        User user = userMapper.queryById(userGrade.getUId());
        IndexNation nation = nationMapper.queryById(user.getNation());
        if(sum>nation.getNationLevel()){
            userGrade.setIndexSum((float)nation.getNationCode());
        }else{
            userGrade.setIndexSum(sum/ nation.getNationLevel()* nation.getNationCode());
        }

        if(gradeMapper.update(userGrade)==1) {
            if(mapper.update(passage)==1){
                return 1;
            }else{
                throw new Exception("passage更新出错！");
            }
        }else{
            throw new Exception("grade更新出错！");
        }
    }

    private float getGrade(GradePassage passage){
        int level = passage.getLevel();
        if(level==1){ //顶尖期刊
            float grade = DTOP;
            if(passage.getSchoolOrder()>0){
                grade = grade/passage.getSchoolOrder();
            }
            int length = passage.getAuthorOrder();
            for (int i=0;i<length-1;i++){
                grade/=2;
            }
            return grade;

        }else if(level==2){ //TOP级
            if(passage.getAuthorOrder()==1){
                return TOP;
            }else{
                return 0;
            }
        }else if(level==3){ //A级
            if (passage.getAuthorOrder()==1){
                return A;
            }else {
                return 0;
            }

        }else if(level==4){ //B级
            if (passage.getAuthorOrder()==1){
                return B;
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }

    private float calculateSum(List<GradePassage> passageList,long id){
        float sumPass=0l;
        int numTOP = 0,numA=0,numB=0;
        for (GradePassage pass:passageList) {
            int level = pass.getLevel();
            if(pass.getPassageId()==null||pass.getPassageId()==id){//要删除的论文
                continue;//不计算这篇论文
            }
            if(level==1){ //顶尖期刊
                sumPass+=pass.getPassageGrade();
            }else if(level==2){ //TOP级
                if(pass.getPassageGrade()>0){
                    numTOP++;
                }
            }else if(level==3){ //A级
                if(pass.getPassageGrade()>0){
                    numA++;
                }
            }else if(level==4){ //B级
                if(pass.getPassageGrade()>0){
                    numB++;
                }
            }else {
                continue;
            }
        }

        if(numTOP>3){
            sumPass+=((Math.pow(Math.E,4-numTOP)/12+3)*TOP);
        }else{
            sumPass+=(numTOP*TOP);
        }
        if (numA>3){
            sumPass+=((Math.pow(Math.E,4-numTOP)/12+3)*A);
        }else {
            sumPass+=(numA*A);
        }
        if(numB>1){
            sumPass+=((Math.pow(Math.E,2-numTOP)/12+1)*B);
        }else{
            sumPass+=(numB*B);
        }
        return sumPass;
    }
}
