package com.springboot.web.restful.springbootwebrestful.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author Lee
 * @create 2020/4/24 13:53
 */
@Controller
public class LoginController {

  @RequestMapping("/user/login")
  public String login(
      @RequestParam("username") String username,
      @RequestParam("password") String password,
      HttpSession session) {
    if (username.equals("admin") && password.equals("111111")) {
      session.setAttribute("user", username);
      return "redirect:/main.html";
    } else {
      session.setAttribute("msg", "用户名或密码错误");
      return "redirect:/index.html";
    }
  }
}
