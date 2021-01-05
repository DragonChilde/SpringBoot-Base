package com.sb.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/** @title: RequestParamController @Author Wen @Date: 2021/1/5 11:43 @Version 1.0 */
@Controller
public class RequestParamController {

  @GetMapping("/goto")
  public String goToPage(HttpServletRequest request) {

    request.setAttribute("msg", "成功了...");
    request.setAttribute("code", 200);
    return "forward:/success"; // 转发到  /success请求
  }

  //获取指定的attribute值
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

  //获取HttpServletRequest 对象
  @ResponseBody
  @GetMapping("/success")
  public Map success(HttpServletRequest request) {
    Map<String, Object> map = new HashMap<>();

    Object code = request.getAttribute("code");
    Object msg = request.getAttribute("msg");

    map.put("msg", msg);
    map.put("code", code);
    return map;
  }
}
