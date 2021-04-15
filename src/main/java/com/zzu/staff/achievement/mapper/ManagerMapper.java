package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ManagerMapper {

    Manager queryById(long id);
}
