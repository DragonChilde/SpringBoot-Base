package com.web.thymeleaf.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** @title: CustomerHandlerExceptionResolver @Author Wen @Date: 2021/1/18 15:56 @Version 1.0 */
@Order(value = Ordered.HIGHEST_PRECEDENCE) // 优先级，数字越小优先级越高
@Component
public class CustomerHandlerExceptionResolver implements HandlerExceptionResolver {
  @Override
  public ModelAndView resolveException(
      HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    try {
      response.sendError(511, "自定义的错误解析器");
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new ModelAndView();
  }
}
