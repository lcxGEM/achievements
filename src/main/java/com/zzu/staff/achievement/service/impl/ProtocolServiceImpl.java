package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.entity.Protocol;
import com.zzu.staff.achievement.mapper.ProtocolMapper;
import com.zzu.staff.achievement.service.IProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProtocolServiceImpl implements IProtocolService {

    @Autowired
    private ProtocolMapper mapper;

    @Override
    public Protocol queryById(int id) {
        return mapper.queryById(id);
    }

    @Override
    public int update(Protocol protocol) {
        return mapper.update(protocol);
    }
}
