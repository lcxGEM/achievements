package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.Protocol;

public interface IProtocolService {

    Protocol queryById(int id);

    int update(Protocol protocol);
}
