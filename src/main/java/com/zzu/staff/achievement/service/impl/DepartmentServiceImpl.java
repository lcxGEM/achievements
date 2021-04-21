package com.zzu.staff.achievement.service.impl;

import com.zzu.staff.achievement.service.IDepartmentService;
import com.zzu.staff.achievement.entity.Department;
import com.zzu.staff.achievement.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> queryAll() {
        return departmentMapper.queryAll();
    }
}
