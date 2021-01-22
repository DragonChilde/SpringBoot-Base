package com.web.thymeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan(basePackages = "com.web.thymeleaf")
@SpringBootApplication
public class ThymeleafApplication {

  public static void main(String[] args) {
    SpringApplication.run(ThymeleafApplication.class, args);
  }
}
