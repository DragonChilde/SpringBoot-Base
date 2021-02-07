package com.springboot.web.restful.springbootwebrestful;

import com.springboot.web.restful.springbootwebrestful.component.MyLocaleResolve;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class SpringbootWebRestfulApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootWebRestfulApplication.class, args);
  }
}
