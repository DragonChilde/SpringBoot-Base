package com.sb.annotation;

import com.sb.annotation.Bean.Pet;
import com.sb.annotation.Bean.User;
import com.sb.annotation.config.MyConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.sb.annotation")
// 上面三个注解的作用等同于@SpringBootApplication
//@SpringBootApplication
public class AnnotationApplication {

  public static void main(String[] args) {
    // 1.返回我们IOC容器
    ConfigurableApplicationContext run = SpringApplication.run(AnnotationApplication.class, args);

    // 2.查看容器里面的组件
    String[] names = run.getBeanDefinitionNames();

    for (String name : names) {
      System.out.println(name);
    }

    // 3. 从容器中获取组件
    Pet tom01 = run.getBean("tom", Pet.class);
    Pet tom02 = run.getBean("tom", Pet.class);
    System.out.println("组件: " + (tom01 == tom02)); // 组件: true

    // 4.配置类也会加载进容器中com.sb.annotation.config.MyConfig$$EnhancerBySpringCGLIB$$76a3fdda@1a82d0f
    MyConfig myConfig = run.getBean(MyConfig.class);
    System.out.println(myConfig);

    // 如果@Configuration(proxyBeanMethods = true)代理对象调用方法。SpringBoot总会检查这个组件是否在容器中有。
    // 保持组件单实例
    User user = myConfig.user();
    User user1 = myConfig.user();
    System.out.println(user == user1); // true

    // 如果@Configuration(proxyBeanMethods = false)SpringBoot跳过检查,每次都他建新的容器对象
    User user2 = run.getBean("user", User.class);
    Pet tom = run.getBean("tom", Pet.class);

    System.out.println("用户的宠物:" + (user2.getPet() == tom)); // 用户的宠物:false

    boolean book = run.containsBean("book");
    System.out.println("容器中Book组件:" + book);

    boolean haha = run.containsBean("haha");
    boolean hehe = run.containsBean("hehe");
    System.out.println("haha: "+haha);
    System.out.println("hehe: "+hehe);

    Car car = run.getBean(Car.class);
    System.out.println(car);
  }
}
