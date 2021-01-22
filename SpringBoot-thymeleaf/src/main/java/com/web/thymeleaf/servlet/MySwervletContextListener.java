package com.web.thymeleaf.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @title: MySwervletContextListener
 * @Author Wen
 * @Date: 2021/1/18 19:31
 * @Version 1.0
 */
@Slf4j
@WebListener
public class MySwervletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        log.info("MySwervletContextListener监听到项目初始化完成");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        log.info("MySwervletContextListener监听到项目销毁");
    }
}