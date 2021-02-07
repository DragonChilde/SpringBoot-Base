package com.sb.annotation.config;

import com.sb.annotation.Bean.Color;
import com.sb.annotation.Bean.Pet;
import com.sb.annotation.Bean.User;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/** @title: MyConfig @Author Wen @Date: 2020/12/29 13:41 @Version 1.0 */
/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的 2、配置类本身也是组件 3、proxyBeanMethods：代理bean的方法(SpringBoot2.3新增)
 * Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的】 Lite(proxyBeanMethods =
 * false)【每个@Bean方法被调用多少次返回的组件都是新创建的】
 * 实际开发中,如果每次都需要组件依赖必须使用Full模式默认。其他默认可以选择Lite模式(好处是启动的时候不用每次都检查是否在容器中,直接创建一个新的容器,加快启动速度)
 */
// @EnableConfigurationProperties(Car.class)
@ImportResource("classpath:beans.xml")
@Import({Color.class})
@Configuration(proxyBeanMethods = true)
// @ConditionalOnBean(name = "book")      //当容器中有tom时才进行加载
@ConditionalOnMissingBean(name = "book") // 当容器中没有tom时才进行加载
public class MyConfig {

  /**
   * Full:外部无论对配置类中的这个组件注册方法调用多少次获取的都是之前注册容器中的单实例对象
   *
   * @return
   */
  @Bean // 给容器中添加组件。以方法名作为组件的id。返回类型就是组件类型。返回的值，就是组件在容器中的实例
  public User user() {
    User zhangsan = new User("zhangsan", 20);
    // user组件依赖了Pet组件
    zhangsan.setPet(pet());
    return zhangsan;
  }

  @Bean("tom")
  public Pet pet() {
    return new Pet("Tomcat");
  }
}
