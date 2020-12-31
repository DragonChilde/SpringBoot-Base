package com.springboot.config.springbootconfig;

import com.springboot.config.springbootconfig.bean.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@ImportResource(locations = {"classpath:bean.xml"})
@SpringBootApplication
public class SpringbootConfigApplication {

  public static void main(String[] args) {
    ConfigurableApplicationContext run =
        SpringApplication.run(SpringbootConfigApplication.class, args);
    User user = run.getBean(User.class);
    System.out.println(user);
  }
}
