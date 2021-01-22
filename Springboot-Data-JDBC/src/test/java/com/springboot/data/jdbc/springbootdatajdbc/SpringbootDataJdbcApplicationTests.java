package com.springboot.data.jdbc.springbootdatajdbc;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;

@Slf4j
@SpringBootTest
class SpringbootDataJdbcApplicationTests {

  @Autowired DataSource dataSource;

  @Autowired JdbcTemplate jdbcTemplate;

  @Test
  void contextLoads() throws SQLException {
    System.out.println(dataSource.getClass());

    System.out.println(dataSource.getConnection());

    Long aLong = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM user", Long.class);

    log.info("check data count:" + aLong);
  }
}
