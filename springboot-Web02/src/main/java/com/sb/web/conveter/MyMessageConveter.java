package com.sb.web.conveter;

import com.sb.web.bean.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/** @title: MyMessageConveter @Author Wen @Date: 2021/1/8 17:06 @Version 1.0 */
public class MyMessageConveter implements HttpMessageConverter<Person> {
  @Override
  public boolean canRead(Class<?> clazz, MediaType mediaType) {
    return false;
  }

  @Override
  public boolean canWrite(Class<?> clazz, MediaType mediaType) {
    return clazz.isAssignableFrom(Person.class);
  }

  /**
   * 服务器要统计所有MessageConverter都能写出哪些内容类型
   *
   * <p>application/x-test
   *
   * @return
   */
  @Override
  public List<MediaType> getSupportedMediaTypes() {
    return MediaType.parseMediaTypes("application/x-test");
  }

  @Override
  public Person read(Class<? extends Person> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    return null;
  }

  @Override
  public void write(Person person, MediaType contentType, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {

    // 自定义协议数据的写出
    String data = person.getUserName() + ";" + person.getAge() + ";" + person.getBirth();
    OutputStream body = outputMessage.getBody();
    body.write(data.getBytes());
  }
}
