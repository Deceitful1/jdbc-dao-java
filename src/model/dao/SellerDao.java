package model.dao;

import entities.Department;
import entities.Seller;

import java.util.List;

public interface SellerDao
{

    void insert(Seller seller);

    void update(Seller seller);

    void deleteById(Integer id);

    Seller findById(Integer id);

    List<Seller> findAll();
    List<Seller> findByDepartment(Department department);
}
