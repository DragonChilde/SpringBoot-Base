package com.springboot.starter.springbootstarter.config;

import org.springframework.context.annotation.Configuration;

/** @title: MyConfig @Author Wen @Date: 2021/1/21 11:03 @Version 1.0 */
@Configuration
public class MyConfig {

  /**
   * 当重新自定义加载HelloService后,Stater的配置类失效
   * @return
   */
  /*@Bean
  public HelloService helloService() {
    HelloService helloService = new HelloService();

    return helloService;
  }*/
}
