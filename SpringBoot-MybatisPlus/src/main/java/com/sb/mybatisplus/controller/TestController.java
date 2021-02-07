package com.sb.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sb.mybatisplus.bean.User;
import com.sb.mybatisplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @title: TestController
 * @Author Wen
 * @Date: 2021/2/4 10:22
 * @Version 1.0
 */
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/mp/list")
    public String testSql() {
        List<User> list = userService.list();
        return list.toString();
    }

    @RequestMapping("/mp/page")
    public String testSqlPage(@RequestParam("current") Integer current, @RequestParam("limit") Integer limit) {
        Page<User> page = new Page<>(current, limit);
        Page<User> userPage = userService.page(page, null);
        long pageCurrent = userPage.getCurrent();   //当前页
        long total = userPage.getTotal();   //总数据
        long pages = userPage.getPages();   //总页数
        System.out.println(pageCurrent);
        System.out.println(total);
        System.out.println(pages);
        return userPage.getRecords().toString();    //当开启分页插件时,数据从这里获取
    }

    @RequestMapping("/mp/create")
    public void testSqlCreate(User user)
    {
        boolean insert = userService.save(user);
        System.out.println(insert);
    }

    @RequestMapping("/mp/update")
    public void testSqlUpdate(User user)
    {
        boolean update = userService.updateById(user);
        System.out.println(update);
    }

    @RequestMapping("/mp/del")
    public void testSqlDel(@RequestParam("id") Long id)
    {
        boolean del = userService.removeById(id);
        System.out.println(del);
    }
}