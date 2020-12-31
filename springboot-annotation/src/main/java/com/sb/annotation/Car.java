package com.sb.annotation;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @title: Car
 * @Author Wen
 * @Date: 2020/12/30 11:56
 * @Version 1.0
 */
@ToString
@Data
@Component
@ConfigurationProperties(prefix = "mycar")
public class Car {

    private String brand;
    private String price;
}