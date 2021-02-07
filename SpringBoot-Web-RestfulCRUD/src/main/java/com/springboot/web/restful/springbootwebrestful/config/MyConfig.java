package com.springboot.web.restful.springbootwebrestful.config;

import com.springboot.web.restful.springbootwebrestful.component.LoginHandleInterceptor;
import com.springboot.web.restful.springbootwebrestful.component.MyLocaleResolve;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author Lee
 * @create 2020/4/22 14:27
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {

  // 定义不拦截路径,注意SpringBott2.0以上会把静态资源也拦截了,必须把静态webjars也排除
  String[] url = {
    "/", "/index", "/index.html", "/user/login", "/asserts/**", "/webjars/**", "/hello", "/error"
  };

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    // super.addViewControllers(registry);
    // 浏览器发送 /atguigu 请求来到 success
    registry.addViewController("/").setViewName("login");
    registry.addViewController("index").setViewName("login");
    registry.addViewController("index.html").setViewName("login");
    registry.addViewController("main.html").setViewName("dashboard");
  }

  // 注册拦截器
  public void addInterceptors(InterceptorRegistry registry) {
    // 添加不拦截的路径，SpringBoot已经做好了静态资源映射，所以我们不用管
    registry.addInterceptor(new LoginHandleInterceptor()).excludePathPatterns(url);
  }

  @Bean
  public LocaleResolver localeResolver() {
    return new MyLocaleResolve();
  }
}
