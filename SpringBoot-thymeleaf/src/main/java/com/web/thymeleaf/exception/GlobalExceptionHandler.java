package com.web.thymeleaf.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** @title: GlobalExceptionHandler @Author Wen @Date: 2021/1/18 14:58 @Version 1.0 */
/** 处理整个web controller的异常 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({ArithmeticException.class, NullPointerException.class}) // 处理异常
  /** 可台返回ModelAndView ,即包含视图也包含模型数据 */
  public String handleArithException(Exception e) {
    log.error("异常是:{}", e);
    return "login"; // 视图地址
  }
}
