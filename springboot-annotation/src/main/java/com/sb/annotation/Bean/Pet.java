package com.sb.annotation.Bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/** @title: Pet @Author Wen @Date: 2020/12/29 13:46 @Version 1.0 */
@Data
@NoArgsConstructor
public class Pet {

  private String name;

  public Pet(String name) {
    this.name = name;
  }
}
