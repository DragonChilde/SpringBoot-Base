package com.springboot.web.restful.springbootwebrestful.config;

import com.springboot.web.restful.springbootwebrestful.component.LoginHandleInterceptor;
import com.springboot.web.restful.springbootwebrestful.component.MyLocaleResolve;
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

    String[] url = {"/","/index.html","/user/login"};

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // super.addViewControllers(registry);
        //浏览器发送 /atguigu 请求来到 success
        registry.addViewController("/").setViewName("login");
        registry.addViewController("index").setViewName("login");
        registry.addViewController("index.html").setViewName("login");
        registry.addViewController("main.html").setViewName("dashboard");
    }


    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandleInterceptor()).excludePathPatterns(url);
    }

    @Bean
    public LocaleResolver localeResolver()
    {
        return new MyLocaleResolve();
    }

}
