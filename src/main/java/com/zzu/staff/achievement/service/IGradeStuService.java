package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.GradeStu;

import java.util.List;

public interface IGradeStuService {

    GradeStu insert(GradeStu stu);

    List<GradeStu> queryByGradeId(long id);

    int deleteById(long id) throws Exception;

    int update(GradeStu stu) throws Exception;

    int insertS(GradeStu stu) throws Exception;
}
