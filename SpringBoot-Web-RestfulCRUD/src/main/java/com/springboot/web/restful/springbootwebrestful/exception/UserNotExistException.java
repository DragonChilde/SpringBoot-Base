package com.springboot.web.restful.springbootwebrestful.exception;

/**
 * @author Lee
 * @create 2020/5/7 16:03
 */
public class UserNotExistException extends RuntimeException {

  public UserNotExistException() {
    super("用户不存在");
  }
}
