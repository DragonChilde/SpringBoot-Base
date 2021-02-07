package com.springboot.starter.springbootstarter.Controller;

import com.springboot.starter.define.configure.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  @Autowired HelloService helloService;

  @GetMapping("/hello")
  public String Hello() {
    return helloService.sayHello("张三");
  }
}
