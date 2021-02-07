package com.springboot.starter.define.configure.service;

import com.springboot.starter.define.configure.bean.HelloProperties;

/** 默认不要放在容器中 */
public class HelloService {

  HelloProperties helloProperties;

  public HelloProperties seHelloProperties(HelloProperties helloProperties) {
    return this.helloProperties = helloProperties;
  }

  public HelloProperties getHelloProperties() {
    return this.helloProperties;
  }

  public String sayHello(String name) {
    return this.helloProperties.getPrefix()
        + " ---- "
        + name
        + "----"
        + this.helloProperties.getSuffix();
  }
}
