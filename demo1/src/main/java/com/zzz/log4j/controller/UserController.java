package com.zzz.log4j.controller;

import com.zzz.log4j.domain.User;
import com.zzz.log4j.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by dell_2 on 2016/9/6.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    public Object test(){
        User user = new User();
        user.setId(1);
        user.setUserName("zhangsan");
        user.setPassword("123456");
        userService.addUser(user);
        return user;
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    public Object get(@PathVariable("id") Integer id){
        User user = userService.get(id);
        return user;
    }

    @RequestMapping("/update/{id}")
    @ResponseBody
    public Object update(@PathVariable("id") Integer id){
        userService.update(id);
        return "updateSuccess";
    }
}
