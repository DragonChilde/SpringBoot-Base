package com.sb.web.config;

import com.sb.web.bean.Pet;
import com.sb.web.conveter.MyMessageConveter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @title: WebConfig @Author Wen @Date: 2021/1/4 11:02 @Version 1.0 */
@Configuration
public class WebConfig /*implements WebMvcConfigurer*/ {

  @Bean
  public HiddenHttpMethodFilter hiddenHttpMethodFilter() {

    HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
    hiddenHttpMethodFilter.setMethodParam("_m");
    return hiddenHttpMethodFilter;
  }

  // 第一种方式,继承WebMvcConfigurer,重写configurePathMatch
  // 继承WebMvcConfigurer接口,根据JDK8新特性,无需所有方法全部重写,只需重写我们需要的特定方法
  /* @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    UrlPathHelper urlPathHelper = new UrlPathHelper();
    urlPathHelper.setRemoveSemicolonContent(false);
    configurer.setUrlPathHelper(urlPathHelper);
  }*/

  //1、WebMvcConfigurer定制化SpringMVC的功能
  @Bean
  public WebMvcConfigurer webMvcConfigurer() {
    return new WebMvcConfigurer() {

      /**
       * 自定义协商策略,根据请求参数处理
       *
       * @param configurer
       */
      @Override
      public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {

        Map<String, MediaType> mediaTypes = new HashMap<>();
        mediaTypes.put("json", MediaType.APPLICATION_JSON);
        mediaTypes.put("xml", MediaType.APPLICATION_XML);
        mediaTypes.put("gg", MediaType.parseMediaType("application/x-test"));
        ParameterContentNegotiationStrategy strategy =
            new ParameterContentNegotiationStrategy(mediaTypes);
        //增加请求头策略,兼容自定义参数策略
        HeaderContentNegotiationStrategy headerStrategy = new HeaderContentNegotiationStrategy();
        configurer.strategies(Arrays.asList(strategy, headerStrategy));
      }

      /**
       * 自定义协商策略,根据请求头处理
       * @param converters
       */
      @Override
      public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MyMessageConveter());
      }

      @Override
      public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        // 不移除后面的内容.矩阵变量就可以生效
        urlPathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(urlPathHelper);
      }

      @Override
      public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(
            new Converter<String, Pet>() {
              @Override
              public Pet convert(String source) {
                // 啊猫,3
                if (!StringUtils.isEmpty(source)) {
                  Pet pet = new Pet();
                  String[] split = source.split(",");
                  pet.setName(split[0]);
                  pet.setAge(Integer.parseInt(split[1]));
                  return pet;
                }
                return null;
              }
            });
      }
    };
  }
}
