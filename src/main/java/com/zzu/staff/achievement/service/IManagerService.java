package com.zzu.staff.achievement.service;

import com.zzu.staff.achievement.entity.Manager;

public interface IManagerService {

    Manager doLogin(long id, String passwd);
}
