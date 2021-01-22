package com.web.thymeleaf.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/** @title: MyFilter @Author Wen @Date: 2021/1/18 19:28 @Version 1.0 */
@Slf4j
@WebFilter(urlPatterns = {"/css/*", "/images/*"})
public class MyFilter implements Filter {
  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    log.info("MyFilter工作");
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    log.info("MyFilter初始化完成");
  }

  @Override
  public void destroy() {
    log.info("MyFilter销毁");
  }
}
