package com.springboot.config.springbootconfig.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lee
 * @create 2020/4/8 11:00
 */
@RestController
public class HelloController {

    @Value("${person.name}")
    private String name;

    @RequestMapping("hello")
    public String hello()
    {
        return  "Hello "+this.name;
    }

}
