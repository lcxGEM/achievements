package com.zzu.staff.achievement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.UserRecord;
import com.zzu.staff.achievement.entity.UserRecordVo;
import com.zzu.staff.achievement.service.IUserRecordService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/staff/")
public class UserRecordController {

    @Autowired
    private IUserRecordService staffService;

    /**
     * 职工前端展示信息
     * 分页 模糊查询功能
     */
    @GetMapping("queryAllVo/{pageNum}/{pageSize}/{sName}/{teacherType}/{gsType}/{msType}/{dsType}/{depart}")
    public PageInfo<UserRecordVo> queryAllVo(@PathVariable("pageNum")int pageNum,
                                             @PathVariable("pageSize") int pageSize,
                                             @PathVariable("sName")String sName, //模糊查询数据：姓名关键字
                                             @PathVariable("teacherType")Integer teacherType,
                                             @PathVariable("gsType")Integer gsType, //模糊查询数据：学校关键字
                                             @PathVariable("msType")Integer msType, //
                                             @PathVariable("dsType")Integer dsType,
                                             @PathVariable("depart")Integer depart){ //

        PageHelper.startPage(pageNum, pageSize);
        List<UserRecordVo> schoolVoList = staffService.queryAllVo(sName,teacherType,gsType,msType,dsType,depart);
        PageInfo<UserRecordVo> pageInfo = new PageInfo<>(schoolVoList);
        return pageInfo;
    }

    @GetMapping("queryByUsId/{id}")
    public UserRecordVo queryById(@PathVariable("id")Long id){
        return staffService.queryById(id);
    }

    @GetMapping("queryRecordByUsId/{id}")
    public UserRecord queryRecordByUsId(@PathVariable("id")Long id){
        UserRecord record = staffService.queryRecordByUsId(id);
        if(record==null){
            return new UserRecord(-1l,-1l,0l,0l,0l,0f,null);
        }
        return record;
    }

    @GetMapping("delete/{id}")
    public int delete(@PathVariable("id")long id){
        return staffService.deleteById(id);
    }

    @PostMapping("update")
    public int update(UserRecord staff){
        return staffService.update(staff);
    }

    @PostMapping("staffAdd")
    public int staffAdd(UserRecord staff){
        //-1出错
        return staffService.insert(staff);
    }

    @GetMapping("deleteAll/{ids}")
    public int deleteAll(@PathVariable("ids")String ids){
        String [] ida = ids.split(",");
        int a=0;
        for (String id:ida) {
            if(id.length()>0){
                Long aid = new Long(id);
                if(aid>0){
                    int b = staffService.deleteById(aid);
                    a+=b;
                }
            }
        }
        return a;
    }

    @GetMapping("updateAll/{stId}")
    public int updateAll(@PathVariable("stId")Integer stId){
        return staffService.updateAll(stId);
    }

    /**
     * 从数据库导出Excel数据
     * @param response
     */
    @GetMapping("downloadAll/{stId}")
    public void downloadAll(HttpServletResponse response, @PathVariable("stId") Integer stId){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("郑州大学教师学历排名表");
        List<UserRecordVo> classmateList = staffService.queryAllVo(" ",-1,0,0,0,0);
        SimpleDateFormat a = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = "TeacherCompositeIndex_"+ a.format(new Date()) + ".xls";//设置要导出的文件的名字
        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        String[] headers = { "岗位类别", "姓名", "性别", "联系方式","身份证","本科学校","硕士学校","博士学校","综合指数","评价","院系"};
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
        for (UserRecordVo teacher : classmateList) {
            HSSFRow row1 = sheet.createRow(rowNum);
            row1.createCell(0).setCellValue(teacher.getNationName());
            row1.createCell(1).setCellValue(teacher.getUserName());
            row1.createCell(2).setCellValue(teacher.getSex()==1?"男":"女");
            row1.createCell(3).setCellValue(teacher.getPhoneNumber());
            row1.createCell(4).setCellValue(teacher.getIdcard());
            row1.createCell(5).setCellValue(teacher.getuSchoolName());
            row1.createCell(6).setCellValue(teacher.getmSchoolName());
            row1.createCell(7).setCellValue(teacher.getdSchoolName());
            row1.createCell(8).setCellValue(teacher.getCompositeIndex());
            row1.createCell(9).setCellValue(teacher.getEvaluation());
            row1.createCell(10).setCellValue(teacher.getDepartmentName());
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
}
