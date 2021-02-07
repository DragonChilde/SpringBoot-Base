package com.springboot.config.springbootconfig;

import com.springboot.config.springbootconfig.bean.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@SpringBootTest
class SpringbootConfigApplicationTests {

  @Autowired private Person person;

  @Autowired private ApplicationContext ioc;

  @Test
  public void contextLoads() {}

  @Test
  public void testPersonConfig() {

    System.out.println(person);
  }

  @Test
  public void testHello() {
    System.out.println(ioc.containsBean("hello02"));
  }
}
