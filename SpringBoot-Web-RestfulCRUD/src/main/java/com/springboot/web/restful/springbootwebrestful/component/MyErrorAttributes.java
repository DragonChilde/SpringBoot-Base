package com.springboot.web.restful.springbootwebrestful.component;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

// 给容器中加入我们自己定义的ErrorAttributes
@Component
public class MyErrorAttributes extends DefaultErrorAttributes {

  // 返回值的map就是页面和json能获取的所有字段
  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
    // 调用父类的方法获取默认的数据
    Map<String, Object> map = super.getErrorAttributes(webRequest, includeStackTrace);
    // 从request域从获取到自定义数据
    Map<String, Object> ext =
        (Map<String, Object>) webRequest.getAttribute("ext", RequestAttributes.SCOPE_REQUEST);
    map.putAll(ext);
    return map;
  }
}
