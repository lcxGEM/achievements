package com.zzu.staff.achievement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.*;
import com.zzu.staff.achievement.service.IUserGradeService;
import com.zzu.staff.achievement.service.IUserService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserGradeService gradeService;
    //--------------------用户基本信息方面的----------------------------
    @GetMapping("queryAll/{page}/{size}/{name}/{idCard}/{nation}/{category}/{departId}")
    public PageInfo<UserVo> queryAll(@PathVariable("page")Integer pageNum,
                                     @PathVariable("size")Integer pageSize,
                                     @PathVariable("name")String name,
                                     @PathVariable("idCard")String idCard,
                                     @PathVariable("nation")Integer nation,
                                     @PathVariable("category")Integer category,
                                     @PathVariable("departId")Integer departId){
        PageHelper.startPage(pageNum, pageSize);
        List<UserVo> userParams = userService.queryAll(name, idCard, nation, category, departId);
        PageInfo<UserVo> pageInfo = new PageInfo<UserVo>(userParams);
        return pageInfo;
    }

    @GetMapping("queryByDepartment/{id}")
    public List<User> queryByDepartment(@PathVariable("id")Integer id){
        return  userService.queryByDepartment(id);
    }

    //展示基本信息时使用
    @GetMapping("queryById/{id}")
    public User queryById(@PathVariable("id")Long id){
        return userService.queryById(id);
    }

    @GetMapping("doLogin/{tel}/{passwd}")
    public UserOut doLogin(@PathVariable("tel")String tel, @PathVariable("passwd")String passwd){
        User user = userService.doLogin(tel,passwd);
        UserOut userOut = new UserOut();
        userOut.setUserId(user.getUserId().toString());
        userOut.setUserName(user.getUserName());
        userOut.setPasswd(user.getPasswd());
        userOut.setNation(user.getNation());
        userOut.setDepartmentId(user.getDepartmentId());
        return userOut;
    }

    //教师注册
    @PostMapping("insert")
    public long insert(User user){
        user.setPasswd(user.getIdcard().substring(user.getIdcard().length()-8));
        return userService.insertA(user);
    }

    //基本信息更新
    @PostMapping("update")
    public int update(User user) throws Exception {
        return userService.update(user);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id){
        return userService.deleteById(id);
    }

    @GetMapping("/changePasswd/{id}/{passwd}")
    public int changePasswd(@PathVariable("id")Long id,@PathVariable("passwd")String passwd){
        return userService.changePasswd(id,passwd);
    }

    //-----------------------业绩信息显示---------------------------------
    @GetMapping("queryAllParam/{pageNum}/{pageSize}/{year}/{cate}/{sName}/{sTel}/{sId}/{sDepart}/{status}")
    public PageInfo<UserParam> queryParam(@PathVariable("pageNum")Integer pageNum,
                                          @PathVariable("pageSize") Integer pageSize,
                                          @PathVariable("year")Integer year,
                                          @PathVariable("cate")Integer category,
                                          @PathVariable("sName") String sName,
                                          @PathVariable("sTel") String sTel,
                                          @PathVariable("sId") String sId,
                                          @PathVariable("sDepart") Integer sDepart,
                                          @PathVariable("status")Integer status){
        PageHelper.startPage(pageNum, pageSize);
        List<UserParam> userParams = userService.queryAllParam(year, category, sName, sTel, sId, sDepart,status);
        PageInfo<UserParam> pageInfo = new PageInfo<>(userParams);
        return pageInfo;
    }

    @GetMapping("queryUserParam/{id}")
    public List<UserParam> queryUserParam(@PathVariable("id")Long id){
        List<UserParam> userParams = userService.queryUserParam(id);
        return userParams;
    }

    @GetMapping("changeStatus/{id}/{status}")
    public int changeStatus(@PathVariable("id")Long id,@PathVariable("status")Integer status){
        return gradeService.updateStatus(id,status);
    }

    @GetMapping("deleteGrade/{id}")
    public int deleteGrade(@PathVariable("id")Long id){
        return gradeService.deleteById(id);
    }

    //教师端分项数据需要的grade
    @GetMapping("getGrade/{id}")
    public String getGrade(@PathVariable("id")Long id){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        UserGrade userGrade = gradeService.queryByUIdAndYear(id, year);
        if(userGrade==null){
            return "-1";
        }else{
            return userGrade.getGradeId().toString();
        }
    }

    @GetMapping("insertUserGrade/{id}")
    public String insertUserGrade(@PathVariable("id")Long id) {
        UserGrade userGrade = new UserGrade(null,id,Calendar.getInstance().get(Calendar.YEAR),0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,0f,false,0);
        return gradeService.insertA(userGrade).toString();
    }

    @GetMapping("downloadAll/{year}/{cate}/{sName}/{sTel}/{sId}/{sDepart}/{status}")
    public void downloadAll(HttpServletResponse response, @PathVariable("year")Integer year,
                            @PathVariable("cate")Integer category,
                            @PathVariable("sName") String sName,
                            @PathVariable("sTel") String sTel,
                            @PathVariable("sId") String sId,
                            @PathVariable("sDepart") Integer sDepart,
                            @PathVariable("status")Integer status){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        cellStyle.setDataFormat(dataFormat.getFormat("0.0"));
        cellStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
        HSSFSheet sheet = workbook.createSheet("郑州大学教师绩效表");
        List<UserParam> userParams = userService.queryAllParam(year, category, sName, sTel, sId, sDepart,status);
        SimpleDateFormat a = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "TeacherAchievement_"+ a.format(new Date()) + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"岗位类别", "人员类别","院系", "姓名", "身份证", "指导博士(后)","人才项目","论文","科研项目","获奖情况","专利","成果转化",
                "业绩总分","学历总分","换算业绩总分", "换算学历总分","换算合计","合格状态","状态"};
        //headers表示excel表中第一行的表头
        HSSFRow row = sheet.createRow(0);
        //在excel表中添加表头
        for(int i=0;i<headers.length;i++){
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        //在表中存放查询到的数据放入对应的列
        for (UserParam param : userParams) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.setRowStyle(cellStyle);
            row1.createCell(0).setCellValue(param.getNationName());
            row1.createCell(1).setCellValue(param.getPeopleCategory());
            row1.createCell(2).setCellValue(param.getDepartmentName());
            row1.createCell(3).setCellValue(param.getUserName());
            row1.createCell(4).setCellValue(param.getIdcard());
            row1.createCell(5).setCellValue(param.getStu());
            row1.createCell(6).setCellValue(param.getTalent());
            row1.createCell(7).setCellValue(param.getPassage());
            row1.createCell(8).setCellValue(param.getProgram());
            row1.createCell(9).setCellValue(param.getPrize());
            row1.createCell(10).setCellValue(param.getPatent());
            row1.createCell(11).setCellValue(param.getResult());
            row1.createCell(12).setCellValue(param.getSum());
            row1.createCell(13).setCellValue(param.getComSum()==null?0:param.getComSum());
            row1.createCell(14).setCellValue(param.getIndexSum());
            row1.createCell(15).setCellValue(param.getIndexComSum());
            row1.createCell(16).setCellValue(param.getResSum());
            row1.createCell(17).setCellValue(param.getIsRes()?"合格":"不合格");

            row1.createCell(18).setCellValue(getStatus(param.getStatus()));
            rowNum++;
        }
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        try {
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getStatus(int status){
        if(status==1){
            return "待审核";
        }else if(status==3){
            return "审核不过";
        }else if(status==2){
            return "审核通过";
        }else {
            return "";
        }
    }
}
