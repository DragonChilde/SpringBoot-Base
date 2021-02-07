package com.sb.web.controller;

import com.sb.web.bean.Person;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/** @title: RequestParamController @Author Wen @Date: 2021/1/5 11:43 @Version 1.0 */
@RestController
public class RequestParamController {

  @GetMapping("/goto")
  public String goToPage(HttpServletRequest request) {

    request.setAttribute("msg", "成功了...");
    request.setAttribute("code", 200);
    return "forward:/success"; // 转发到  /success请求
  }

  // 获取指定的attribute值
  /*  @ResponseBody
  @GetMapping("/success")
  public Map success(
      @RequestAttribute(value = "msg", required = false) String msg,
      @RequestAttribute(value = "code", required = false) Integer code) {
    Map<String, Object> map = new HashMap<>();

    map.put("msg", msg);
    map.put("code", code);

    return map;
  }*/

  // 获取HttpServletRequest 对象
  @ResponseBody
  @GetMapping("/success")
  public Map success(HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>();

    Object code = request.getAttribute("code");
    Object msg = request.getAttribute("msg");
    Object hello = request.getAttribute("hello");
    Object world = request.getAttribute("world");

    map.put("msg", msg);
    map.put("code", code);
    map.put("hello", hello);
    map.put("world", world);
    return map;
  }

  @GetMapping("/params")
  public String testParam(
      Map<String, Object> map,
      Model model,
      HttpServletRequest request,
      HttpServletResponse response) {
    map.put("hello", "world666");
    model.addAttribute("world", "hello666");
    request.setAttribute("message", "HelloWorld");

    Cookie cookie = new Cookie("c1", "v1");
    response.addCookie(cookie);
    return "forward:/success";
  }

  /**
   * 数据绑定：页面提交的请求数据（GET、POST）都可以和对象属性进行绑定
   *
   * @param person
   * @return
   */
  @PostMapping("/saveuser")
  public Person saveuser(Person person) {

    return person;
  }
}
