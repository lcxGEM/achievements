package com.zzu.staff.achievement.util;

import com.zzu.staff.achievement.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HandlerDataUtil {

    public static User handlerUser(Map<String,Object> user){
        String userName = (String) user.get("userName");
        Integer sex = new Integer((String) user.get("sex"));
        Integer people = new Integer((String) user.get("peopleCategory"));//1在职 2引进
        String nativePlace = (String) user.get("nativePlace");
        String politicsStatus = (String) user.get("politicsStatus");
        String nation = (String) user.get("nation");
        String tel = (String) user.get("phoneNumber");
        String idCard = (String) user.get("idcard");
        Integer department = new Integer((String) user.get("departmentId"));
        User nUser = new User(null,userName,idCard.substring(idCard.length()-8),sex,people,
                nativePlace,politicsStatus, nation,tel,idCard,department);
        return nUser;
    }

    public static List<GradeStu> handlerStu(List<Map<String,Object>> stuList){
        List<GradeStu> nStuList = new ArrayList<>();
        for (Map<String,Object> stu:stuList) {
            Integer education = new Integer((String) stu.get("education"));
            if(education==0){
                continue;
            }
            String stuName = (String) stu.get("stuName");
            String stuId = (String) stu.get("stuId");
            Boolean boxin = (new Integer((String) stu.get("boxin")))==1?true:false;
            Boolean bote = (new Integer((String) stu.get("bote")))==1?true:false;
            GradeStu nStu = new GradeStu(null,null,education,stuName,
                    stuId,boxin,bote,null);
            nStuList.add(nStu);
        }
        return nStuList;
    }

    public static GradeTalent handlerTalent(Map<String, Object> talent) {
        Integer talentId = new Integer((String)talent.get("talentId"));
        String url = (String) talent.get("gradeUrl");
        return new GradeTalent(null,talentId,url,null);
    }

    public static List<GradePassage> handlerPassage(List<Map<String,Object>> passages,int flag){
        List<GradePassage> passageList = new ArrayList<>();
        System.out.println(passages.toString());
        for (Map<String,Object> pass:passages) {
            System.out.println("----------------------"+pass.toString());
            Integer level = new Integer((String) pass.get("level"));
            String passName = (String) pass.get("passageName");
            String doi = (String) pass.get("DOI");
            Integer school;
            if(flag==1){
                school = new Integer((String) pass.get("schoolOrder"));
            }else{
                school = -1;
            }
            Integer author = new Integer((String) pass.get("authorOrder"));
            if(level==0||author==0){
                continue;
            }
            Boolean isOne = (new Integer((String) pass.get("isOne")))==1?true:false;
            Integer noeNum = new Integer((String) pass.get("oneNum"));
            Integer partition = new Integer((String) pass.get("partition"));
            String journal = (String) pass.get("journal");
            String passageDate = (String) pass.get("passageDate");
            String passageUrl1 = (String) pass.get("passageUrl1");
            String passageUrl2 = (String) pass.get("passageUrl2");
            GradePassage passage = new GradePassage(null,null,level,passName,doi,school,author,
                    isOne,noeNum,partition,journal,passageDate,passageUrl1,passageUrl2,null);
            passageList.add(passage);
            System.out.println("---------已装配-------------"+passage.toString());
        }
        return passageList;
    }

    public static List<GradePro> handlerPro(List<Map<String,Object>> pros){
        List<GradePro> proList = new ArrayList<>();
        for (Map<String,Object> pro:pros) {
            String proName = (String) pro.get("proName");
            String proNum = (String) pro.get("proNum");
            Integer proLevel = new Integer((String) pro.get("proLevel"));
            if (proLevel==0){
                continue;
            }
            Boolean host = (new Integer((String) pro.get("host")))==1?true:false;
            String url = (String) pro.get("proUrl");
            GradePro nPro = new GradePro(null,null,proName,proNum,proLevel,host,url,null);
            proList.add(nPro);
        }
        return proList;
    }

    public static List<GradePrize> handlerPrize(List<Map<String,Object>> prizes,int flag){
        List<GradePrize> prizeList = new ArrayList<>();
        for (Map<String,Object> prize:prizes){
            String prizeName = (String) prize.get("prizeName");
            Integer type = new Integer((String) prize.get("prizeType"));
            if (type==0){
                continue;
            }
            Integer level = new Integer((String) prize.get("prizeLevel"));
            String date = (String) prize.get("prizeDate");
            Integer school;
            if(flag==1){//在职
                school = new Integer((String) prize.get("prizeSchoolOrder"));
            }else{//引进
                school = -1;
            }
            Integer author = new Integer((String) prize.get("prizeAuthorOrder"));
            String url = (String) prize.get("prizeUrl");
            GradePrize nPrize = new GradePrize(null,null,prizeName,type,level,date,school,
                    author,url,null);
            prizeList.add(nPrize);
        }
        return prizeList;
    }

    public static List<GradePatent> handlerPatent(List<Map<String,Object>> patents){
        List<GradePatent> patentList = new ArrayList<>();
        for (Map<String,Object> patent:patents){
            String name = (String) patent.get("name");
            Integer nature = new Integer((String) patent.get("nature"));
            if(nature==0){
                continue;
            }
            String num = (String) patent.get("ID");
            Boolean is = (new Integer((String) patent.get("isInventor")))==1?true:false;
            String url = (String) patent.get("patentUrl");
            GradePatent nPatent = new GradePatent(null,null,name,nature,num,is,url,null);
            patentList.add(nPatent);
        }
        return patentList;
    }

    public static List<GradeResult> handlerResult(List<Map<String,Object>> results){
        List<GradeResult> resultList = new ArrayList<>();
        for (Map<String,Object> res : results){
            String name  = (String) res.get("name");
            if(name==null||name.equals("")){
                continue;
            }
            Float num = new Float((String) res.get("num"));
            String url = (String) res.get("resultUrl");
            GradeResult result = new GradeResult(null,null,name,num,null,url);
            resultList.add(result);
        }
        return resultList;
    }
}
