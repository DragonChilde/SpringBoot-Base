package com.springboot.web.jsp.springbootwebjsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("/abc")
  public String hello(Model model) {
    model.addAttribute("hello", "你好");
    return "success";
  }
}
