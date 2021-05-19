package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.GradePassage;
import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.mapper.GradePassageMapper;
import com.zzu.staff.achievement.mapper.UserGradeMapper;
import com.zzu.staff.achievement.mapper.UserMapper;
import com.zzu.staff.achievement.service.IGradePassageService;
import com.zzu.staff.achievement.service.IUserGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 论文
 * 1、直聘研究员-----nation为 2----->只计算中科院一区的论文
 * 2、直聘副研究员----nation为 3---->只计算中科院一区、二区的论文
 * 3、求是学人/求是学者----nation为 9和10---->使用衰减法计算
 */
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

    @Value("${passage.C}")
    private int C;

    @Value("${passage.D}")
    private int D;

    @Autowired
    private GradePassageMapper mapper;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserGradeService gradeService;

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
        GradePassage passage =  mapper.queryById(id);//先获取原论文
        UserGrade userGrade = gradeMapper.queryById(passage.getGradeId());//获取积分
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());//获取教师信息，主要是身份
        if(user==null){
            return -1;
        }
        List<GradePassage> passageList = mapper.queryByGradeId(passage.getGradeId());//获取所有论文
        //计算论文总分
        float sumPass = calculateSum(passageList,id,user.getNation());
        //总分计算完毕，更新userGrade
        float sum = userGrade.getSum()-userGrade.getPassage()+sumPass;
        userGrade.setSum(sum);
        userGrade.setPassage(sumPass);

        if(gradeService.update(userGrade)==1) {
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
        UserGrade userGrade = gradeMapper.queryById(passage.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return -1;
        }
        //1、直聘研究员-----nation为 2----->只计算中科院一区的论文
        if(user.getNation()==2){
            if (passage.getPartition()!=1){//直聘研究员且论文分区不是一区，直接返回，不添加
                return -2;
            }
        }
        //2、直聘副研究员----nation为 3---->只计算中科院一区、二区的论文
        if(user.getNation()==3){
            if(passage.getPartition()!=1&&passage.getPartition()!=2){
                return -2;
            }
        }
        float grade = getGrade(passage);
        passage.setPassageGrade(grade);
        List<GradePassage> passageList = mapper.queryByGradeId(passage.getGradeId());//获取所有论文

        passageList.add(passage);
        float sumPass = calculateSum(passageList,0l,user.getNation());
        //总分计算完毕，更新userGrade
        float sum  = userGrade.getSum()-userGrade.getPassage()+sumPass;
        userGrade.setSum(sum);
        userGrade.setPassage(sumPass);

        if(gradeService.update(userGrade)==1) {
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
        UserGrade userGrade = gradeMapper.queryById(passage.getGradeId());
        if(userGrade.getStatus()==2||userGrade.getStatus()==1){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return -1;
        }
        float grade = getGrade(passage);

        //1、直聘研究员-----nation为 2----->只计算中科院一区的论文
        if(user.getNation()==2){
            if (passage.getPartition()!=1){//直聘研究员且论文分区不是一区，直接返回，不添加
                grade = 0;
            }
        }
        //2、直聘副研究员----nation为 3---->只计算中科院一区、二区的论文
        if(user.getNation()==3){
            if(passage.getPartition()!=1&&passage.getPartition()!=2){
                grade = 0;
            }
        }
        passage.setPassageGrade(grade);
        List<GradePassage> passageList = mapper.queryByGradeId(passage.getGradeId());//获取所有论文
        passageList.remove(passage);
        passageList.add(passage);
        float sumPass = calculateSum(passageList,0l,user.getNation());
        float sum = userGrade.getSum()-userGrade.getPassage()+sumPass;
        userGrade.setSum(sum);
        userGrade.setPassage(sumPass);
        if(gradeService.update(userGrade)==1) {
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
        }else if(level==5){ //C级
            if (passage.getAuthorOrder()==1){
                return C;
            }else {
                return 0;
            }
        }else if(level==6){ //D级
            if (passage.getAuthorOrder()==1){
                return D;
            }else {
                return 0;
            }
        }else{
            return 0;
        }
    }

    private float calculateSum(List<GradePassage> passageList,long id,int nation){
        float sumPass=0l;
        int numTOP = 0,numA=0,numB=0,numC=0,numD=0;
        for (GradePassage pass:passageList) {
            int level = pass.getLevel();
            if(pass.getPassageId()==null||pass.getPassageId()==id){//要删除的论文
                continue;//不计算这篇论文
            }
            if(pass.getPassageGrade()<=0){ //论文分为0，不计算的论文
                continue;
            }
            if(level==1){ //顶尖期刊
                sumPass+=pass.getPassageGrade();
            }else if(level==2){ //TOP级
                numTOP++;
            }else if(level==3){ //A级
                numA++;
            }else if(level==4){ //B级
                numB++;
            }else if(level==5){ //C级
                numC++;
            }else if(level==6){ //D级
                numD++;
            }else {
                continue;
            }
        }
        if(nation==9||nation==10) { //3、求是学人/求是学者----nation为 9和10---->使用衰减法计算
            if (numTOP > 3) {
                sumPass += ((Math.pow(Math.E, 4 - numTOP) / 12 + 3) * TOP);
            } else {
                sumPass += (numTOP * TOP);
            }
            if (numA > 3) {
                sumPass += ((Math.pow(Math.E, 4 - numTOP) / 12 + 3) * A);
            } else {
                sumPass += (numA * A);
            }
            if (numB > 1) {
                sumPass += ((Math.pow(Math.E, 2 - numTOP) / 12 + 1) * B);
            } else {
                sumPass += (numB * B);
            }
        }else{
            sumPass = sumPass + numTOP*TOP + numA*A + numB*B ;
        }
        sumPass = sumPass + numC*C +numD*D;
        return sumPass;
    }
}
