package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.GradeTalent;

public interface IGradeTalentService {

    GradeTalent insert(GradeTalent talent);

    GradeTalent queryByGradeId(long id);

    int update(GradeTalent talent) throws Exception;

    int insertA(GradeTalent talent) throws Exception;
}
