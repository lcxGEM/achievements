package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.UserRecord;
import com.zzu.staff.achievement.entity.UserRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRecordMapper {

    UserRecordVo queryById(long id);

    int insert(UserRecord userRecord);

    int deleteById(long id);

    int deleteByUserId(long id);

    int update(UserRecord userRecord);

    List<UserRecordVo> searchAllVo(@Param("trim")String trim,
                              @Param("teacherType")int teacherType,
                              @Param("gsType")Integer gsType,
                              @Param("msType")int msType,
                              @Param("dsType")int dsType,
                              @Param("depart")int depart);

    List<UserRecord> queryAll();

    UserRecord queryRecordByUsId(long id);
}
