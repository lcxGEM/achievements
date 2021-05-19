package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserParam;
import com.zzu.staff.achievement.entity.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<UserVo> queryAll(@Param("name") String name,
                          @Param("idCard") String idCard,
                          @Param("nation") Integer nation,
                          @Param("category") Integer category,
                          @Param("departId") Integer departId);

    List<User> queryByDepartment(int id);

    User queryById(long id);

    //登录时调用
    User queryByTel(String tel);

    int insert(User user);

    int insertA(User user);

    int update(User user);

    int deleteById(long id);

    int changePasswd(Long id, String passwd);

    List<UserParam> queryAllParamByParam(@Param("year") int year,
                                         @Param("category") int category,
                                         @Param("sName") String sName,
                                         @Param("sTel") String sTel,
                                         @Param("sId") String sId,
                                         @Param("sDepart") int sDepart,
                                         @Param("status")int status);

    List<UserParam> queryUserParam(Long id);
}
