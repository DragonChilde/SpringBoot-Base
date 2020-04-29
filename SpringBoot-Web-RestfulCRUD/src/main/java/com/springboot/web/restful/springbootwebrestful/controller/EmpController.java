package com.springboot.web.restful.springbootwebrestful.controller;

import com.springboot.web.restful.springbootwebrestful.dao.DepartmentDao;
import com.springboot.web.restful.springbootwebrestful.dao.EmployeeDao;
import com.springboot.web.restful.springbootwebrestful.entities.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;


@Controller
public class EmpController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    @GetMapping("/emps")
    public String list(Model model)
    {
        model.addAttribute("emps",employeeDao.getAll());
        return "/emp/list";
    }

    @GetMapping("/emp")
    public String toAddEmpPage(Model model)
    {
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("deps",departments);
        return "/emp/add";
    }
}
