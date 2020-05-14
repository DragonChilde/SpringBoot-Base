package com.springboot.data.mybatis.springbootdatamybatis.mapper;

import com.springboot.data.mybatis.springbootdatamybatis.entity.Employee;

import java.util.List;


public interface EmployeeMapper {

    public List<Employee> getAll();

    public Employee getById(Integer id);
}
