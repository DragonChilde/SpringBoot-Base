package com.springboot.data.mybatis.springbootdatamybatis.controller;

import com.springboot.data.mybatis.springbootdatamybatis.entity.Department;
import com.springboot.data.mybatis.springbootdatamybatis.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptController {

  @Autowired private DepartmentMapper departmentMapper;

  @GetMapping("/dept/{id}")
  public Department getDept(@PathVariable("id") Integer id) {
    return departmentMapper.getById(id);
  }

  @GetMapping("/dept")
  public Department addDept(Department department) {
    departmentMapper.add(department);
    return department;
  }
}
