package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.User;
import com.zzu.staff.achievement.entity.UserParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryAll();

    List<UserParam> queryAllParam(@Param("year") int year,
                                  @Param("category") int category,
                                  @Param("sName") String sName,
                                  @Param("sTel") String sTel,
                                  @Param("sId") String sId,
                                  @Param("status")int status);

    List<UserParam> queryAllParamByParam(@Param("year") int year,
                                         @Param("category") int category,
                                         @Param("sName") String sName,
                                         @Param("sTel") String sTel,
                                         @Param("sId") String sId,
                                         @Param("sDepart") int sDepart,
                                         @Param("status")int status);
    List<User> queryByDepartment(int id);

    User queryById(long id);

    int insert(User user);

    int insertA(User user);

    int update(User user);

    int deleteById(long id);

    User queryByTel(String tel);

    int changePasswd(String id, String passwd);

    List<UserParam> queryUserParam(String tel);

    User queryByIdCard(String idcard);
}
