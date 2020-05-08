package com.springboot.web.restful.springbootwebrestful.controller;

import com.springboot.web.restful.springbootwebrestful.exception.UserNotExistException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HWErrorController {

    /*1、浏览器客户端返回的都是json*/
    @GetMapping("/hello")
    public String error(@RequestParam("user")String user)
    {
        if (user.equals("aaa"))
        {
            throw new UserNotExistException();
        }
        return "HELLO WORLD";
    }

}
