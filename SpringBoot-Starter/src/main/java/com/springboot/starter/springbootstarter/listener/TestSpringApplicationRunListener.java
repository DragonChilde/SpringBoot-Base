package com.springboot.starter.springbootstarter.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author Lee
 * @create 2020/5/14 16:49
 */
public class TestSpringApplicationRunListener implements SpringApplicationRunListener {
    @Override
    public void starting() {
        System.out.println("TestSpringApplicationRunListener starting...");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println("TestSpringApplicationRunListener environmentPrepared...");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println("TestSpringApplicationRunListener contextPrepared...");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println("TestSpringApplicationRunListener contextLoaded...");
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        System.out.println("TestSpringApplicationRunListener started...");
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        System.out.println("TestSpringApplicationRunListener running...");
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println("TestSpringApplicationRunListener failed...");
    }

    //必须有的构造器
   public TestSpringApplicationRunListener(SpringApplication application, String[] args) {
    }
}
