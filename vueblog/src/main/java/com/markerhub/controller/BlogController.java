package com.markerhub.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.markerhub.common.lang.Result;
import com.markerhub.entity.Blog;
import com.markerhub.service.BlogService;
import com.markerhub.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 * @since 2020-11-25
 */
@RestController
public class BlogController {

    //控制层

    @Autowired
    BlogService blogService;


    @GetMapping("/blogList")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){

        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return  Result.succ(pageData);

    }

    @GetMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") long id){

        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"该博客被删除");
        return  Result.succ(blog);

    }

    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit(@Validated @RequestBody Blog blog){

        Blog temp = null;
        //判断id是否存在,存在修改,不存在添加
        if(blog.getId()!=null){
            temp=blogService.getById(blog.getId());
            //只能编辑自己的文档
            Assert.isTrue(temp.getUserId().longValue()== ShiroUtil.getProfile().getId().longValue(),"没有权限编辑");
        }else{
            temp = new Blog();
            temp.setUserId(ShiroUtil.getProfile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtil.copyProperties(blog,temp,"id","userId","created","status");
        blogService.saveOrUpdate(temp);
        return  Result.succ(null);
    }
}
