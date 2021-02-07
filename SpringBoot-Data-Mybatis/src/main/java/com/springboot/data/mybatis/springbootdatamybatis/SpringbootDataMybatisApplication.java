package com.springboot.data.mybatis.springbootdatamybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.springboot.data.mybatis.springbootdatamybatis.mapper")
@SpringBootApplication
public class SpringbootDataMybatisApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootDataMybatisApplication.class, args);
  }
}
