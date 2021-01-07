package com.sb.web.controller;

import com.sb.web.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/** @title: ResponseTestController @Author Wen @Date: 2021/1/7 10:37 @Version 1.0 */
@Controller
public class ResponseTestController {

  @ResponseBody
  @GetMapping(value = "/test/person")
  public Person getPerson() {
    Person person = new Person();
    person.setUserName("lisi");
    person.setAge(28);
    person.setBirth(new Date());
    return person;
  }
}
