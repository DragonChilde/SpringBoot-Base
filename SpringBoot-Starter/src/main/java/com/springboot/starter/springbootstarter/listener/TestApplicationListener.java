package com.springboot.starter.springbootstarter.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * @title: TestApplicationListener
 * @Author Wen
 * @Date: 2021/1/21 17:34
 * @Version 1.0
 */
public class TestApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("MyApplicationListener.....onApplicationEvent...");
    }
}