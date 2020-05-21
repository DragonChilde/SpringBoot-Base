package com.springboot.starter.define.configure.starter;


public class HelloService {

    HelloProperties helloProperties;

    public HelloProperties seHelloProperties(HelloProperties helloProperties){
        return this.helloProperties = helloProperties;
    }

    public HelloProperties getHelloProperties()
    {
        return this.helloProperties;
    }

    public String sayHello(String name)
    {
        return this.helloProperties.getPrefix() + " ---- " + name + "----"+ this.helloProperties.getSuffix();
    }

}
