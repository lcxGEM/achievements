package com.zzu.staff.achievement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.School;
import com.zzu.staff.achievement.entity.SchoolData;
import com.zzu.staff.achievement.entity.SchoolVo;
import com.zzu.staff.achievement.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学校信息部分
 */
@RestController
@RequestMapping("/school/")
public class SchoolController {

    @Autowired
    private SchoolService service;

    /**
     * 获取所有信息（根据数据库表）
     * @return
     */
    @GetMapping("queryAll")
    public List<School> queryAll(){
        return service.queryAll();
    }

    /**
     * 获取前端要显示的学校信息 包含分页功能
     * @param pageNum
     * @param pageSize
     * @param sName
     * @param sType
     * @return
     */
    @GetMapping("queryAllVo/{pageNum}/{pageSize}/{sName}/{sType}")
    public PageInfo<SchoolVo> queryAllVo(@PathVariable("pageNum")int pageNum,  //分页页数
                                         @PathVariable("pageSize") int pageSize, //每页数据
                                         @PathVariable("sName") String sName,
                                         @PathVariable("sType")Integer sType){
        PageHelper.startPage(pageNum, pageSize);
        List<SchoolVo> schoolVoList = service.queryAllVo(sName,sType);
        PageInfo<SchoolVo> pageInfo = new PageInfo<>(schoolVoList);
        return pageInfo;
    }

    /**
     * 学校信息做成layui select所需要的数据格式 已经弃用
     * @return
     */
//    @GetMapping("queryAllData")
//    public Map<String,Object> queryAllData(){
//        List<School> schools = service.queryAll();
//
//        List<SchoolData> schoolDatas = new ArrayList<>();
//        for (School s: schools) {
//            SchoolData schoolData = new SchoolData(s.getId().toString(),s.getName());
//            schoolDatas.add(schoolData);
//        }
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("code", 0);
//        map.put("data", schoolDatas);
//        map.put("count", schoolDatas.size());
//        map.put("msg", "成功");
//        return map;
//    }


    @PostMapping("add")
    public int add(School school){
        return service.insert(school);
    }

    @GetMapping("delete/{id}")
    public int deleteById(@PathVariable("id")long id){
        return service.deleteById(id);
    }

    @PostMapping("update")
    public int update(School school){
        return service.update(school);
    }

    @GetMapping("deleteAll/{ids}")
    public int deleteAll(@PathVariable("ids")String ids){
        String [] ida = ids.split(",");
        int a=0;
        for (String id:ida) {
            if(id.length()>0){
                long aid = new Long(id);
                if(aid>0){
                    int b = service.deleteById(aid);
                    a+=b;
                }
            }
        }
        return a;
    }

    /**
     * 导入Excel中的数据
     * @param file
     * @param schoolType
     * @return
     */
    @RequestMapping("import/{schoolType}")
    @ResponseBody
    public int excelImport(@RequestParam(value="filename")MultipartFile file,@PathVariable("schoolType") Integer schoolType){
        System.out.println("-------------------->\n"+file.getContentType()+"\n--------------------------");
        int result = 0;
        try {
            result = service.importData(file,schoolType);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("addOther/{name}")
    public long addOther(@PathVariable("name")String name) throws Exception {
        return service.addOther(name);
    }
}
