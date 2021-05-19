package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.mapper.*;
import com.zzu.staff.achievement.service.IUserGradeService;
import com.zzu.staff.achievement.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserGradeServiceImpl implements IUserGradeService {

    @Autowired
    private UserGradeMapper mapper;

    @Autowired
    private UserMapper userMapper;

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
    private IndexNationMapper nationMapper;

    @Autowired
    private UserRecordMapper recordMapper;

    @Override
    public UserGrade queryByUIdAndYear(long uId, int year) {
        return mapper.queryByUIdAndYear(uId,year);
    }

    @Override
    public UserGrade queryById(long id) {
        return mapper.queryById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int deleteById(long id) {
        passageMapper.deleteByGradeId(id);
        patentMapper.deleteByGradeId(id);
        prizeMapper.deleteByGradeId(id);
        proMapper.deleteByGradeId(id);
        resultMapper.deleteByGradeId(id);
        stuMapper.deleteByGradeId(id);
        talentMapper.deleteByGradeId(id);
        return mapper.deleteById(id);
    }

//    @Override
//    public int insert(UserGrade userGrade) {
//        return mapper.insert(userGrade);
//    }

    @Override
    public Long insertA(UserGrade userGrade) {
        Long id = IdWorker.getId();
        userGrade.setGradeId(id);
        if(mapper.insertA(userGrade)==1){
            return id;
        }else{
            return -1l;
        }
    }

    @Override
    public int update(UserGrade userGrade) {
        //查询user和indexNation
        User user = userMapper.queryById(userGrade.getUId());
        if(user==null){
            return -1;
        }
        IndexNation indexNation = nationMapper.queryById(user.getNation());
        if (indexNation==null){
            return -1;
        }
        // 已经有 sum userId，计算indexSum 通过 indexNation进行折合
        float sum =  userGrade.getSum();
        if(sum>=indexNation.getNationLevel()){//大于标准值，折合分直接满分
            userGrade.setIndexSum(indexNation.getNationCode());
        }else{//小于的话折合计算
            userGrade.setIndexSum(sum/ indexNation.getNationLevel()*indexNation.getNationCode());
        }
        // 计算comIndex 学历分（查询userRecord表）
        UserRecordVo record = recordMapper.queryById(user.getUserId());
        float com = 0;
        if(record!=null){
            com = record.getCompositeIndex();
        }
        userGrade.setComSum(com);
        // 学历折合indexComSum 通过indexNation
        if(com>=indexNation.getCompositeLevel()){
            userGrade.setIndexComSum(indexNation.getCompositeCode());
        }else{
            userGrade.setIndexComSum(com/indexNation.getCompositeLevel()*indexNation.getCompositeCode());
        }
        // 总分resSum= indexSum+indexComSum
        userGrade.setResSum(userGrade.getIndexSum()+userGrade.getIndexComSum());
        // 是否合格
        userGrade.setIsRes(userGrade.getSum()>=indexNation.getQualified());

        return mapper.update(userGrade);
    }

    //最后调用update防法，调用前检验状态修改是否合法
    @Override
    public int updateStatus(Long id, Integer status) {
        UserGrade userGrade = mapper.queryById(id);
        //先判断是不是所有数据都为空
        if(userGrade.getSum()==0){
            return -2;
        }
        User user = userMapper.queryById(userGrade.getUId());
        //特殊条件：求是学者”提交时，人才项目不能为空
        if(user.getNation()==10){//求是学人
            if(status==1){//提交时
                if(userGrade.getStu()==0){//人才项目为空
                    return -3;
                }
            }
        }

        if(userGrade.getStatus()==1){
            if(status!=2&&status!=3){
                return -1;
            }
        }
        userGrade.setStatus(status);
        return mapper.update(userGrade);
    }
}
