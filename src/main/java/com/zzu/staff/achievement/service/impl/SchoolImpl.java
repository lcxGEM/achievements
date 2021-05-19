package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.School;
import com.zzu.staff.achievement.entity.SchoolVo;
import com.zzu.staff.achievement.mapper.SchoolMapper;
import com.zzu.staff.achievement.mapper.SchoolTypeMapper;
import com.zzu.staff.achievement.service.SchoolService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolImpl implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private SchoolTypeMapper schoolTypeMapper;

    @Override
    public List<School> queryAll() {
        return schoolMapper.queryAll();
    }

    @Override
    public List<SchoolVo> queryAllVo(String sName,Integer sType) {
        String trim = sName.trim();
        if(trim.length()==0&&sType==0){
            return schoolMapper.queryAllVo();
        }else {
            return schoolMapper.searchAllVo(trim,sType);
        }
    }

    @Override
    public School queryById(long id) {
        return schoolMapper.queryById(id);
    }

    @Override
    public int insert(School school) {
        return schoolMapper.insert(school);
    }

    @Override
    public int deleteById(long id) {
        return schoolMapper.deleteById(id);
    }

    @Override
    public int update(School school) {
        return schoolMapper.update(school);
    }

    /**
     * 可是实现多次导入，但是会将之前的数据全部删除，会导致之前的所有人的学校id丧失，这样会导致之前的数据全部消失
     * @param file
     * @param schoolType
     * @return
     * @throws IOException
     */
    @Override
    public int importData(MultipartFile file,int schoolType) throws IOException {
        schoolMapper.deleteByType(schoolType);
        int result = 0;
        List<School> schools = new ArrayList<>();
        //判断文件版本
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        InputStream ins = file.getInputStream();
        Workbook wb = null;
        if(suffix.equals("xlsx")){
            wb = new XSSFWorkbook(ins);

        }else if(suffix.equals("xls")){
            wb = new HSSFWorkbook(ins);
        }else {
            return -1;
        }
        //获取excel表单
        Sheet sheet = wb.getSheetAt(0);
        // line = 1 :从表的第二行开始获取记录
        if(null != sheet){
            int a = 0;
            for(int line = 1; line <= sheet.getLastRowNum();line++){
                Row row = sheet.getRow(line);
                if(null == row){
                    continue;
                }
                if(line==1){
                    a =(int)row.getCell(0).getNumericCellValue();
                }
                Integer sort = a++;
                String name = row.getCell(1).getStringCellValue();
                School school = new School(null,name,schoolType,sort);
                schools.add(school);
            }
        }
        for (School school:schools) {
            System.out.println(school.toString());
            result+=schoolMapper.insert(school);
        }
        return result;
    }

    @Override
    public long addOther(String name) throws Exception {
        int type = schoolTypeMapper.queryByName("其他高校").getId();
        School school = new School(null,name,type,99);
        int a = schoolMapper.insert(school);
        if(a==1){
            return schoolMapper.queryByName(name).getId();
        }else{
            throw new Exception("添加新学校失败！！");
        }
    }
}
