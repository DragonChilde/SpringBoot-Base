package com.springboot.data.jdbc.springbootdatajdbc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {

  // 默认的自动配置是判断容器中没有才会配@ConditionalOnMissingBean(DataSource.class)
  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean
  public DataSource druid() {
    DruidDataSource dataSource = new DruidDataSource();
    try {
      dataSource.setFilters("stat,wall");
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return dataSource;
  }

  // 配置Druid的监控页面功能
  // 1、配置一个管理后台的Servlet
  @Bean
  public ServletRegistrationBean statViewServlet() {
    ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    Map<String, String> initParams = new HashMap<>();

    initParams.put("loginUsername", "admin");
    initParams.put("loginPassword", "admin");
    initParams.put("allow", ""); // 默认就是允许所有访问
    bean.setInitParameters(initParams);
    return bean;
  }

  // 2、配置一个web监控的filter
  @Bean
  public FilterRegistrationBean webStatFilter() {
    WebStatFilter webStatFilter = new WebStatFilter();

    FilterRegistrationBean<WebStatFilter> filterRegistrationBean =
        new FilterRegistrationBean<>(webStatFilter);

    filterRegistrationBean.addInitParameter(
        "exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

    filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));

    return filterRegistrationBean;
  }
}
