package com.markerhub.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    //测试使用
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;
    @RequiresAuthentication
    @GetMapping("/index")
    public Result index(){
        User user=userService.getById(1);

        return  Result.succ(user);
    }
    @RequestMapping("/select")
    public Result selectUser(){
        User user = userMapper.selectById(1);
        return Result.succ(user);
    }

    //校验测试
    @PostMapping("/save")
    public Result save(@Validated  @RequestBody User user){

        return Result.succ(user);
    }


}
