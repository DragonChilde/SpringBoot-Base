package com.springboot.config.springbootconfig.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** @title: User @Author Wen @Date: 2020/12/31 11:23 @Version 1.0 */
@Component
@ConfigurationProperties(prefix = "user")
@ToString
@Data
public class User {

  private String userName;
  private Boolean boss;
  private Date birth;
  private Integer age;
  private Pet pet;
  private String[] interests;
  private List<String> animal;
  private Map<String, Object> score;
  private Set<Double> salarys;
  private Map<String, List<Pet>> allPets;
}
