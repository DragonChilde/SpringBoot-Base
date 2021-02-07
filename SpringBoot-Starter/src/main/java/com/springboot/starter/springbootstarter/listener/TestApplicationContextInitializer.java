package com.springboot.starter.springbootstarter.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class TestApplicationContextInitializer implements ApplicationContextInitializer {
  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    System.out.println(
        "TestApplicationContextInitializer initialize..." + configurableApplicationContext);
  }
}
