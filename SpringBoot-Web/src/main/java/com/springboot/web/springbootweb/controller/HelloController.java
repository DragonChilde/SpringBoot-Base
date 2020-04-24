package com.springboot.web.springbootweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Lee
 * @create 2020/4/20 17:41
 */
@Controller
public class HelloController {

    @RequestMapping("/success")
    public String success(Model model)
    {
       model.addAttribute("hello","<h1>你好</h1>");
       model.addAttribute("users", Arrays.asList("张三","李四","王五"));

        return "success";
    }

}
