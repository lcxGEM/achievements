package com.zzu.staff.achievement.mapper;

import com.zzu.staff.achievement.entity.Protocol;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProtocolMapper {

    Protocol queryById(int id);

    int update(Protocol protocol);
}
