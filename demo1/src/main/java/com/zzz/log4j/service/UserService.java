package com.zzz.log4j.service;

import com.zzz.log4j.domain.User;

/**
 * Created by dell_2 on 2016/9/6.
 */
public interface UserService {

    int addUser(User user);

    int update(Integer id);

    User get(Integer id);


}
