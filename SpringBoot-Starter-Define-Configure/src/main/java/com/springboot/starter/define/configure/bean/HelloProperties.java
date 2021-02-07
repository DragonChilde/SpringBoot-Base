package com.springboot.starter.define.configure.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "define.hello")
public class HelloProperties {

  private String prefix;
  private String suffix;
}
