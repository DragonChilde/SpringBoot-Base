package com.springboot.helloworld.quick.springboothelloworldquick.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 这个类的所有方法返回的数据直接写给浏览器，（如果是对象转为json数据）
// 相当于(@Controller和@ResponseBody一起使用)
@RestController
public class HelloController {

  @RequestMapping("/hello")
  public String hello() {
    return "hello world quick!";
  }
}
