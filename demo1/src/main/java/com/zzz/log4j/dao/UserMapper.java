package com.zzz.log4j.dao;

import com.zzz.log4j.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * Created by dell_2 on 2016/9/6.
 */
public interface UserMapper {

     int add(User user);

     User get(@Param("id") Integer id);
}
