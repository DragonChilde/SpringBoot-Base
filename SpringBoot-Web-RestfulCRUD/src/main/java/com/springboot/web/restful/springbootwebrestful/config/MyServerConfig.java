package com.springboot.web.restful.springbootwebrestful.config;

import com.springboot.web.restful.springbootwebrestful.filter.MyFilter;
import com.springboot.web.restful.springbootwebrestful.listener.MyListener;
import com.springboot.web.restful.springbootwebrestful.servlet.MyServlet;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import java.util.Arrays;

@Configuration
public class MyServerConfig {

    //配置嵌入式的Servlet容器
    @Bean
    public WebServerFactoryCustomizer webServerFactoryCustomizer()
    {
        //定制嵌入式的Servlet容器相关的规则
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                factory.setPort(8081);
            }
        };

    }

    //注册三大组件
    @Bean
    public ServletRegistrationBean myServlet()
    {
        return new ServletRegistrationBean(new MyServlet(), "/myServlet");

    }

    @Bean
    public FilterRegistrationBean myFilter()
    {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilter());
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/myFilter"));
        return filterRegistrationBean;
    }


    @Bean
    public ServletListenerRegistrationBean myListener()
    {
        return new ServletListenerRegistrationBean<>(new MyListener());

    }

}
