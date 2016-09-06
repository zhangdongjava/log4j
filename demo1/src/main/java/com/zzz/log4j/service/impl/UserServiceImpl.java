package com.zzz.log4j.service.impl;

import com.zzz.log4j.dao.UserMapper;
import com.zzz.log4j.domain.User;
import com.zzz.log4j.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by dell_2 on 2016/9/6.
 */
@Service
public class UserServiceImpl implements UserService {

   private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private UserMapper userMapper;

    public int addUser(User user) {
        logger.info("logger add user->"+user);
        userMapper.add(user);
        return 0;
    }
    @Cacheable(value="user",key = "#id")
    public User get(Integer id) {
        return userMapper.get(id);
    }
    @CacheEvict(value="user",key = "#id")
    public int update(Integer id){
        return 0;
    }
}
