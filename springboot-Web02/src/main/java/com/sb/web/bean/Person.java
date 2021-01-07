package com.sb.web.bean;

import lombok.Data;

import java.util.Date;

/**
 * @title: Person
 * @Author Wen
 * @Date: 2021/1/6 16:22
 * @Version 1.0
 */
@Data
public class Person {
    private String userName;
    private Integer age;
    private Date birth;
    private Pet pet;
}