package com.springboot.starter.springbootstarter.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class TestCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("TestCommandLineRunner..."+ Arrays.asList(args));
    }
}
