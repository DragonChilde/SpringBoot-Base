package com.springboot.starter.define.configure.auto;

import com.springboot.starter.define.configure.bean.HelloProperties;
import com.springboot.starter.define.configure.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication // web应用才生效
@EnableConfigurationProperties(HelloProperties.class) // 让配置类生效，(注入到容器中)
public class SpringbootStarterDefineConfiguration {

  @Autowired HelloProperties helloProperties;

  @ConditionalOnMissingBean(HelloService.class) // 当容器里没有HelloService才加载
  @Bean
  public HelloService helloService() {
    HelloService helloService = new HelloService();
    helloService.seHelloProperties(helloProperties);
    return helloService;
  }
}
