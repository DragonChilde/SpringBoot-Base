package com.springboot.starter.define.configure;

import com.springboot.starter.define.configure.starter.HelloProperties;
import com.springboot.starter.define.configure.starter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnWebApplication    //web应用才生效
@EnableConfigurationProperties(HelloProperties.class)   //让配置类生效，(注入到容器中)
public class SpringbootStarterDefineConfigureApplication {

    @Autowired
    HelloProperties helloProperties;

    @Bean
    public HelloService helloService()
    {
        HelloService helloService = new HelloService();
        helloService.seHelloProperties(helloProperties);
        return helloService;
    }

}
