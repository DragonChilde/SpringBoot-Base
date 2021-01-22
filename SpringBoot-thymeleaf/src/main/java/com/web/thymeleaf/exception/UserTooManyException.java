package com.web.thymeleaf.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** @title: UserTooManyException @Author Wen @Date: 2021/1/18 15:20 @Version 1.0 */
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "用户数量太多")
public class UserTooManyException extends RuntimeException {
  public UserTooManyException() {}

  public UserTooManyException(String message) {
    super(message);
  }
}
