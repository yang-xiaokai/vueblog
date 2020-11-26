package com.markerhub.controller;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.User;
import com.markerhub.mapper.UserMapper;
import com.markerhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    //测试使用
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/index")
    public Result index(){
        User user=userService.getById(1);

        return  Result.succ(user);
    }
    @RequestMapping("/select")
    public Result selectUser(){
        Result result =new Result();
        User user = userMapper.selectById(1);
        if(user!=null){
            return Result.succ(user);
        }else{
            return Result.fail("信息错误,请重试!!!");
        }
    }


}
