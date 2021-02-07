package com.springboot.data.mybatis.springbootdatamybatis.mapper;

import com.springboot.data.mybatis.springbootdatamybatis.entity.Department;
import org.apache.ibatis.annotations.*;

// 指定这是一个操作数据库的mapper
// @Mapper
public interface DepartmentMapper {

  @Select("select * from department where id =#{id}")
  public Department getById(Integer id);

  @Options(useGeneratedKeys = true, keyProperty = "id") // 指定自增Key,添加数据后可返回其字段值
  @Insert("insert into department (department_name) values (#{departmentName})")
  public int add(Department department);

  @Update("update department set department_name = #{departmentName} where id = #{id}")
  public int update(Department department);

  @Delete("delete department where id = #{id}")
  public int delete(Integer id);
}
