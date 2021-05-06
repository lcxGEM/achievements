package com.zzu.staff.achievement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserGrade;
import com.zzu.staff.achievement.entity.UserParam;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserGradeService gradeService;

    @GetMapping("queryAll")
    public List<User> queryAll(){
        return userService.queryAll();
    }

    @GetMapping("doLogin/{tel}/{passwd}")
    public User doLogin(@PathVariable("tel")String tel,@PathVariable("passwd")String passwd){
        return userService.doLogin(tel,passwd);
    }

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

    @GetMapping("queryUserParam/{tel}")
    public List<UserParam> queryUserParam(@PathVariable("tel")String tel){
        return userService.queryUserParam(tel);
    }

    @GetMapping("queryByDepartment/{id}")
    public List<User> queryByDepartment(@PathVariable("id")Integer id){
        return  userService.queryByDepartment(id);
    }

    @GetMapping("queryById/{id}")
    public User queryById(@PathVariable("id")Long id){
        return userService.queryById(id);
    }

    @PostMapping("insert")
    public int insert(User user){
        return userService.insert(user);
    }

    @PostMapping("update")
    public int update(User user){
        return userService.update(user);
    }

    @GetMapping("deleteById/{id}")
    public int deleteById(@PathVariable("id")Long id){
        return userService.deleteById(id);
    }

    @GetMapping("/changePasswd/{id}/{passwd}")
    public int changePasswd(@PathVariable("id")String id,@PathVariable("passwd")String passwd){
        return userService.changePasswd(id,passwd);
    }

    @GetMapping("changeStatus/{id}/{status}")
    public int changeStatus(@PathVariable("id")Long id,@PathVariable("status")Integer status){
        UserGrade grade = new UserGrade(id,null,null,null,null,null,null,
                null,null,null,null,status,null);
        return gradeService.update(grade);
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
        HSSFSheet sheet = workbook.createSheet("郑州大学教师绩效表");
        List<UserParam> userParams = userService.queryAllParam(year, category, sName, sTel, sId, sDepart,status);
        SimpleDateFormat a = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "TeacherAchievement_"+ a.format(new Date()) + ".xls";//设置要导出的文件的名字

        HSSFCellStyle sheetStyle = workbook.createCellStyle();
        sheetStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
        sheetStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = {"岗位类别", "人员类别", "姓名", "身份证", "院系","指导博士(后)","人才项目","论文","科研项目","获奖情况","专利","成果转化",
                "总分","换算总分","学历总分","状态"};
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
            row1.createCell(0).setCellValue(param.getNationName());
            row1.createCell(1).setCellValue(param.getPeopleCategory());
            row1.createCell(2).setCellValue(param.getUserName());
            row1.createCell(3).setCellValue(param.getIdcard());
            row1.createCell(4).setCellValue(param.getDepartmentName());
            row1.createCell(5).setCellValue(param.getStu());
            row1.createCell(6).setCellValue(param.getTalent());
            row1.createCell(7).setCellValue(param.getPassage());
            row1.createCell(8).setCellValue(param.getProgram());
            row1.createCell(9).setCellValue(param.getPrize());
            row1.createCell(10).setCellValue(param.getPatent());
            row1.createCell(11).setCellValue(param.getResult());
            row1.createCell(12).setCellValue(param.getSum());
            row1.createCell(13).setCellValue(param.getIndexSum());
            row1.createCell(14).setCellValue(param.getCompositeIndex()==null?0:param.getCompositeIndex());
            row1.createCell(15).setCellValue(getStatus(param.getStatus()));
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
