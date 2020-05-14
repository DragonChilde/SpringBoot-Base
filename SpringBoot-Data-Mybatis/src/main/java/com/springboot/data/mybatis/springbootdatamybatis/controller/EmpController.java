package com.springboot.data.mybatis.springbootdatamybatis.controller;

import com.springboot.data.mybatis.springbootdatamybatis.entity.Employee;
import com.springboot.data.mybatis.springbootdatamybatis.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmpController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/emp/{id}")
    public Employee getEmpById(@PathVariable("id") Integer id){
        return employeeMapper.getById(id);
    }
}
