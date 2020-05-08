package com.springboot.web.restful.springbootwebrestful.controller;

import com.springboot.web.restful.springbootwebrestful.dao.DepartmentDao;
import com.springboot.web.restful.springbootwebrestful.dao.EmployeeDao;
import com.springboot.web.restful.springbootwebrestful.entities.Department;
import com.springboot.web.restful.springbootwebrestful.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@Controller
public class EmpController {

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private DepartmentDao departmentDao;

    //查询所有员工返回列表页面
    @GetMapping("/emps")
    public String list(Model model)
    {
        model.addAttribute("emps",employeeDao.getAll());
        return "/emp/list";
    }

    //员工添加页面
    @GetMapping("/emp")
    public String toAddEmpPage(Model model)
    {
        //来到添加页面,查出所有的部门，在页面显示
        Collection<Department> departments = departmentDao.getDepartments();
        model.addAttribute("deps",departments);
        return "/emp/add";
    }

    //员工添加
    //SpringMVC自动将请求参数和入参对象的属性进行一一绑定；要求请求参数的名字和javaBean入参的对象里面的属性名是一样的
    @PostMapping("/emp")
    public String addEmp(Employee employee)
    {
        System.out.println(employee);
        //保存员工
        employeeDao.save(employee);
        // redirect: 表示重定向到一个地址  /代表当前项目路径
        // forward: 表示转发到一个地址
        return "redirect:/emps";
    }


    //来到修改页面，查出当前员工，在页面回显
    @GetMapping("/emp/{id}")
    public String toEditEmpPage(@PathVariable("id") Integer id,Model model)
    {
        //页面要显示所有的部门列表
        Collection<Department> departments = departmentDao.getDepartments();
        Employee employee = employeeDao.get(id);
        model.addAttribute("emp",employee);
        model.addAttribute("deps",departments);
        //回到修改页面(add是一个修改添加二合一的页面);
        return "/emp/add";
    }

    //员工修改；需要提交员工id；
    @PutMapping("/emp")
    public String editEmp(Employee employee)
    {
        employeeDao.save(employee);
        System.out.println(employee);
        return "redirect:/emps";
    }

    //员工删除
    @DeleteMapping("/emp/{id}")
    public String deleteEmp(@PathVariable("id") Integer id)
    {
        employeeDao.delete(id);
        return "redirect:/emps";
    }


}
