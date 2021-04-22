package com.zzu.staff.achievement.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzu.staff.achievement.entity.IndexPrizeTypeParam;
import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserParam;
import com.zzu.staff.achievement.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("queryAll")
    public List<User> queryAll(){
        return userService.queryAll();
    }

    @GetMapping("doLogin/{tel}/{passwd}")
    public User doLogin(@PathVariable("tel")String tel,@PathVariable("passwd")String passwd){
        return userService.doLogin(tel,passwd);
    }

    @GetMapping("queryAllParam/{pageNum}/{pageSize}/{year}/{cate}/{sName}/{sTel}/{sId}/{sDepart}")
    public PageInfo<UserParam> queryParam(@PathVariable("pageNum")Integer pageNum,
                                          @PathVariable("pageSize") Integer pageSize,
                                          @PathVariable("year")Integer year,
                                          @PathVariable("cate")Integer category,
                                          @PathVariable("sName") String sName,
                                          @PathVariable("sTel") String sTel,
                                          @PathVariable("sId") String sId,
                                          @PathVariable("sDepart") Integer sDepart){
        PageHelper.startPage(pageNum, pageSize);
        List<UserParam> userParams = userService.queryAllParam(year, category, sName, sTel, sId, sDepart);
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
}
