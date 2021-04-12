package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> queryAll();

    List<User> queryByDepartment(int id);

    User queryById(long id);

    int insert(User user);

    int update(User user);

    int deleteById(long id);
}
