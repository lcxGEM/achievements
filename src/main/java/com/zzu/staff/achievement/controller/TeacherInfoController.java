package com.zzu.staff.achievement.controller;


import com.zzu.staff.achievement.config.FtpConfig;
import com.zzu.staff.achievement.config.UploadReturn;
import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.service.*;
import com.zzu.staff.achievement.util.FtpUtil;
import com.zzu.staff.achievement.util.HandlerDataUtil;
import com.zzu.staff.achievement.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/teacherInfo/")
public class TeacherInfoController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserGradeService userGradeService;

    @Autowired
    private IGradeStuService gradeStuService;

    @Autowired
    private IGradeTalentService gradeTalentService;

    @Autowired
    private IGradePassageService passageService;

    @Autowired
    private IGradeProService proService;

    @Autowired
    private IGradePrizeService prizeService;

    @Autowired
    private IGradePatentService patentService;

    @Autowired
    private IGradeResultService resultService;

    @Value("${passage.TOP}")
    private int TOP;

    @Value("${passage.A}")
    private int A;

    @Value("${passage.B}")
    private int B;


    private final int YEAR = Calendar.getInstance().get(Calendar.YEAR);


    @PostMapping("insertInfo")
    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(@RequestBody Map<String,Object> param){
        //获取user
        Map<String,Object> user = (Map<String, Object>) param.get("user");

        //创建user                之后需要创建一个userGrade空表，里面只有uId和id
        User userParam = HandlerDataUtil.handlerUser(user);
        int flag = userParam.getPeopleCategory();
        //user判断是否存在
        User passUser = userService.queryByIdCard(userParam.getIdcard());
        long s;
        if(passUser!=null){
            s = passUser.getUserId();
        }else{
            s = userService.insertA(userParam);//返回user的值。之后要使用
        }
        if(s==-1){
            return -1;
        }
        UserGrade sUserGrade = userGradeService.queryByUIdAndYear(s,YEAR);
        if(sUserGrade!=null){
            return -5;
        }
        //创建userGrade           添加到数据库之后返回id
        UserGrade userGrad = new UserGrade(null,s,YEAR,null,
                null,null, null,null,null,null,null,1);
        long userGrade = userGradeService.insertA(userGrad);
        if(userGrade==-1){
            return -1;
        }

        //每一个service负责算单项分和添加数据，在for循环中计算总分
        //添加stu
        List<Map<String,Object>>  stuS = (List<Map<String, Object>>) param.get("stu");
        List<GradeStu> stuList = HandlerDataUtil.handlerStu(stuS);
        float sumStu  =0l;
        for (GradeStu stu:stuList) {
            stu.setGradeId(userGrade);
            GradeStu nStu = gradeStuService.insert(stu);
            if(nStu==null){
                continue;
            }
            sumStu+=nStu.getStuGrade();
        }

        //添加talent
        Map<String,Object> talent = (Map<String, Object>) param.get("talent");
        GradeTalent talentA = HandlerDataUtil.handlerTalent(talent);
        talentA.setGradeId(userGrade);
        GradeTalent nTalent = gradeTalentService.insert(talentA);

        //添加passage
        List<Map<String,Object>>  passes = (List<Map<String, Object>>) param.get("passage");
        List<GradePassage> passageList = HandlerDataUtil.handlerPassage(passes,flag);
        float sumPass=0l;
        int numTOP = 0,numA=0,numB=0;
        for (GradePassage pass:passageList) {//计算论文总分
            pass.setGradeId(userGrade);
            GradePassage passage = passageService.insert(pass);//计算好单独分数；
            int level = passage.getLevel();
            if(level==1){ //顶尖期刊
                sumPass+=passage.getPassageGrade();
            }else if(level==2){ //TOP级
                if(passage.getPassageGrade()>0){
                    numTOP++;
                }
            }else if(level==3){ //A级
                if(passage.getPassageGrade()>0){
                    numA++;
                }
            }else if(level==4){ //B级
                if(passage.getPassageGrade()>0){
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

        //添加program
        List<Map<String,Object>>  programs = (List<Map<String, Object>>) param.get("program");
        List<GradePro> proList = HandlerDataUtil.handlerPro(programs);
        float sumPro = 0l;
        for (GradePro program:proList) {
            program.setGradeId(userGrade);
            GradePro pro = proService.insert(program);
            sumPro+=pro.getProGrade();
        }

        //添加prize
        List<Map<String,Object>>  prizes = (List<Map<String, Object>>) param.get("prize");
        List<GradePrize> prizeList = HandlerDataUtil.handlerPrize(prizes,flag);
        float sumPrize = 0l;
        for (GradePrize prize:prizeList) {
            prize.setGradeId(userGrade);
            GradePrize prizeA = prizeService.insert(prize);
            sumPrize+=prizeA.getPrizeGrade();
        }

        //添加专利
        List<Map<String,Object>>  patents = (List<Map<String, Object>>) param.get("patent");
        List<GradePatent> patentList = HandlerDataUtil.handlerPatent(patents);
        float sumPatent = 0l;
        for (GradePatent patent:patentList) {
            patent.setGradeId(userGrade);
            GradePatent patentA = patentService.insert(patent);
            sumPatent+=patentA.getPatentGrade();
        }

        //添加成果
        List<Map<String,Object>>  results = (List<Map<String, Object>>) param.get("result");
        List<GradeResult> resultList = HandlerDataUtil.handlerResult(results);
        float sumResult = 0l;
        for (GradeResult result:resultList) {
            result.setGradeId(userGrade);
            GradeResult resultA = resultService.insert(result);
            sumResult+=resultA.getResultGrade();
        }
        float sum = sumStu+ nTalent.getTalentGrade()+sumPass+sumPro+sumPrize+sumPatent+sumResult;

        UserGrade sumGrade = new UserGrade(userGrad.getGradeId(), s, YEAR,sumStu,
                nTalent.getTalentGrade(),sumPass,sumPro,sumPrize,sumPatent,sumResult,sum,1);
        System.out.println("----更新后的绩效--->"+sumGrade.toString());
        return userGradeService.update(sumGrade);
    }

    @PostMapping("uploadFile")
    public UploadReturn uploadFile(MultipartFile file,Integer type,String user,String name) throws IOException {
        FtpConfig config = new FtpConfig();
        String oldName = file.getOriginalFilename();
        String picName = UploadUtil.generateRandonFileName(oldName,type,user,name,YEAR);
        String url = FtpUtil.pictureUploadByConfig(config,picName,"grand",file.getInputStream());
        System.out.println(url);
        return new UploadReturn(url,1);
    }
}
