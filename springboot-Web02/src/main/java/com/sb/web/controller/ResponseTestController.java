package com.sb.web.controller;

import com.sb.web.bean.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/** @title: ResponseTestController @Author Wen @Date: 2021/1/7 10:37 @Version 1.0 */
@Controller
public class ResponseTestController {

  /**
   * 1、浏览器发请求直接返回 xml    [application/xml]        jacksonXmlConverter
   * 2、如果是ajax请求 返回 json   [application/json]      jacksonJsonConverter
   * 3、如果硅谷app发请求，返回自定义协议数据  [appliaction/x-test]   xxxxConverter
   *          属性值1;属性值2;
   *
   * 步骤：
   * 1、添加自定义的MessageConverter进系统底层
   * 2、系统底层就会统计出所有MessageConverter能操作哪些类型
   * 3、客户端内容协商 [test--->test]
   *
   */
  @ResponseBody
  @RequestMapping(value = "/test/person")
  public Person getPerson() {
    Person person = new Person();
    person.setUserName("lisi");
    person.setAge(28);
    person.setBirth(new Date());
    return person;
  }
}
