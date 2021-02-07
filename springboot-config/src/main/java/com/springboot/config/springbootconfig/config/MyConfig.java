package com.springboot.config.springbootconfig.config;

import com.springboot.config.springbootconfig.bean.Hello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration：指明当前类是一个配置类；就是来替代之前的Spring配置文件
 *
 * <p>在配置文件中用<bean><bean/>标签添加组件
 */
// @Profile("dev")
@Configuration
public class MyConfig {

  // 将方法的返回值添加到容器中；容器中这个组件默认的id就是方法名
  @Bean
  public Hello hello02() {
    return new Hello();
  }
}
