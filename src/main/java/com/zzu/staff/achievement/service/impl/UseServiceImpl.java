package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.*;
import com.zzu.staff.achievement.service.*;
import com.zzu.staff.achievement.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

@Service
public class UseServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGradeMapper gradeMapper;

    @Autowired
    private GradePassageMapper passageMapper;

    @Autowired
    private GradePatentMapper patentMapper;

    @Autowired
    private GradePrizeMapper prizeMapper;

    @Autowired
    private GradeProMapper proMapper;

    @Autowired
    private GradeResultMapper resultMapper;

    @Autowired
    private GradeStuMapper stuMapper;

    @Autowired
    private GradeTalentMapper talentMapper;

    @Autowired
    private UserRecordMapper recordMapper;

    @Autowired
    private IUserGradeService gradeService;

    @Autowired
    private IGradeStuService stuService;

    @Autowired
    private IGradeResultService resultService;

    @Autowired
    private IGradeTalentService talentService;

    @Autowired
    private IGradePassageService passageService;

    @Autowired
    private IGradeProService proService;

    @Override
    public List<UserVo> queryAll(String name, String idCard, Integer nation, Integer category, Integer departId) {
        name = name.trim();
        idCard = idCard.trim();
        return userMapper.queryAll(name,idCard,nation,category,departId);
    }

    @Override
    public List<User> queryByDepartment(int id) {
        return userMapper.queryByDepartment(id);
    }

    @Override
    public User queryById(long id) {
        return userMapper.queryById(id);
    }

    @Override
    public User doLogin(String tel, String passwd) {
        User user = userMapper.queryByTel(tel);
        if(user!=null){
            if(user.getPasswd().equals(passwd)){
                return user;
            }
        }
        return null;
    }

    @Override
    public long insertA(User user) {
        long id = IdWorker.getId();
        user.setUserId(id);
        try {
            return userMapper.insertA(user);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    //更新grade之前先要更新user nation
    // 现入今知道的计算规则，
    // 如果原来nation为9,10，那么修改成非9,10的时候
    //     成果转化 result 需要删除全部
    //     指导博士，博士后 需要删除全部
    // 论文 nation=9,10 需要一个随意的更改，然后调用重新计算分数的代码
    // 论文 nation=2 只保留一区的论文，其余全部删除
    // 论文 nation=3 只保留一区、二区的论文，其余全部删除
    // 科研项目 求是学人 nation=9 要删除所有 ProLevel=26的项
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int update(User user) throws Exception {
        //先查看是否处于更新状态
        UserGrade userGrade = gradeMapper.queryByUIdAndYear(user.getUserId(), Calendar.getInstance().get(Calendar.YEAR));
        if(userGrade.getStatus()==1||userGrade.getStatus()==2){//已提交待审核、审核通过两个状态不能随意编辑
            return -3;
        }
        //查询原值，如果nation没有改动，直接更新
        User user1 = userMapper.queryById(user.getUserId());
        if(user.getNation()==user1.getNation()){
            userMapper.update(user);
            return gradeService.update(userGrade);
        }else{
            // 现入今知道的计算规则，
            if((user1.getNation()==9||user1.getNation()==10)&&(user.getNation()!=9&&user.getNation()!=10)){
                // 如果原来nation为9,10，那么修改成非9,10的时候
                //     成果转化 result 需要删除全部
                List<GradeResult> resultList = resultService.queryByGradeId(userGrade.getGradeId());
                if(resultList!=null) {
                    for (GradeResult result : resultList) {
                        resultService.deleteById(result.getResultId());
                    }
                }
                //     指导博士，博士后 需要删除全部
                List<GradeStu> stuList = stuService.queryByGradeId(userGrade.getGradeId());
                if(stuList!=null) {
                    for (GradeStu stu : stuList) {
                        stuService.deleteById(stu.getStuGradeId());
                    }
                }
                //     人才项目 需要全部删除
                GradeTalent talent = talentService.queryByGradeId(userGrade.getGradeId());
                if(talent!=null) {
                    talent.setTalentId(0);
                    talentService.update(talent);
                }
            }
            //更新grade之前先要更新user nation
            userMapper.update(user);
            List<GradePassage> passageList = passageService.queryByGradeId(userGrade.getGradeId());
            if(passageList!=null) {
                if (user.getNation() == 2 || user.getNation() == 3) {
                    for (GradePassage passage : passageList) {
                        // 论文 nation=3 只保留一区、二区的论文，其余全部删除
                        if (passage.getPartition() != 1 && passage.getPartition() != 2) {//既不是一区，也不是二区。全部删除
                            passageService.deleteById(passage.getPassageId());
                        } else {
                            // 论文 nation=2 只保留一区的论文，其余全部删除
                            if (user.getNation() == 2 && passage.getPartition() != 1) {//nation是2且分区不是一区
                                passageService.deleteById(passage.getPassageId());
                            }
                        }
                    }
                }
                // 论文 nation=9,10 需要一个随意的更改，然后调用重新计算分数的代码
                GradePassage passage = passageList.get(0);
                passageService.update(passage);
            }
            // 科研项目 求是学人 nation=9 要删除所有 ProLevel=26的项
            if(user.getNation()==9){
                List<GradeProParam> gradeProParams = proService.queryByGradeId(userGrade.getGradeId());
                if(gradeProParams!=null){
                    for (GradeProParam param: gradeProParams) {
                        if(param.getProLevel()==26){
                            proService.deleteById(param.getProgramId());
                        }
                    }
                }
            }
            userGrade = gradeMapper.queryByUIdAndYear(user.getUserId(), Calendar.getInstance().get(Calendar.YEAR));
            return gradeService.update(userGrade);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) {
        List<UserGrade> userGradeList = gradeMapper.queryByUser(id);
        for (UserGrade grade:userGradeList){
            passageMapper.deleteByGradeId(grade.getGradeId());
            patentMapper.deleteByGradeId(grade.getGradeId());
            prizeMapper.deleteByGradeId(grade.getGradeId());
            proMapper.deleteByGradeId(grade.getGradeId());
            resultMapper.deleteByGradeId(grade.getGradeId());
            stuMapper.deleteByGradeId(grade.getGradeId());
            talentMapper.deleteByGradeId(grade.getGradeId());
            gradeMapper.deleteById(grade.getGradeId());
            recordMapper.deleteById(grade.getUId());
        }
        return userMapper.deleteById(id);
    }

    @Override
    public int changePasswd(Long id, String passwd) {
        return userMapper.changePasswd(id,passwd);
    }

    @Override
    public List<UserParam> queryAllParam(int year,int category, String sName, String sTel, String sId, int sDepart,int status) {
        sName = sName.trim();
        sTel = sTel.trim();
        sId = sId.trim();
        return userMapper.queryAllParamByParam(year,category,sName,sTel,sId,sDepart,status);
    }

    @Override
    public List<UserParam> queryUserParam(Long id) {
        return userMapper.queryUserParam(id);
    }
}
