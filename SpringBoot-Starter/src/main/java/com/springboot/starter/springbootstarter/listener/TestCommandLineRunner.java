package com.springboot.starter.springbootstarter.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 应用启动做一个一次性事情
 */
@Order(2)	//可以定义启动顺序
@Component
public class TestCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("TestCommandLineRunner..."+ Arrays.asList(args));
    }
}
