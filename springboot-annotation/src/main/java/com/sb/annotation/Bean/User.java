package com.sb.annotation.Bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/** @title: User @Author Wen @Date: 2020/12/29 13:42 @Version 1.0 */
@ToString
@NoArgsConstructor
@Data
public class User {

  private String name;
  private Integer age;

  private Pet pet;

  public User(String name, Integer age) {
    this.name = name;
    this.age = age;
  }
}
