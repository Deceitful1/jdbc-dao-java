package model.dao;


import entities.Department;

import java.util.List;

public interface DepartmentDao
{
    void insert(Department obj);
    void update(Department obj);
    void deleteByid(Integer id);
    Department findById(Integer id);
    List<Department> findAll();

}
