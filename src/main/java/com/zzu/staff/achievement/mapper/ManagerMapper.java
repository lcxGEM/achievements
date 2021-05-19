package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ManagerMapper {

    List<Manager> queryAll();

    Manager queryById(int id);

    int insert(Manager manager);

    int deleteById(int id);

    int update(Manager manager);

    List<Manager> searchAll(String trim, int department);

    int changePasswd(int id, String passwd);
}
