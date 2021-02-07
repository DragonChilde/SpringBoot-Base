package com.sb.mybatisplus;

import com.sb.mybatisplus.bean.User;
import com.sb.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisplusApplicationTests {

  @Autowired
  private UserMapper userMapper;

  @Test
  void contextLoads() {

    User user = userMapper.selectById(1L);
    System.out.println(user);

  }
}
