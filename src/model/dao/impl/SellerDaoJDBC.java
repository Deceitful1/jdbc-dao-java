package model.dao.impl;

import entities.Dbexception;
import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao
{
    private Connection conn;

    public SellerDaoJDBC(Connection conn)
    {
        this.conn = conn;
    }


    @Override
    public void insert(Seller seller)
    {

    }

    @Override
    public void update(Seller seller)
    {

    }

    @Override
    public void deleteById(Integer id)
    {

    }

    @Override
    public Seller findById(Integer id)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*, department.Name as Depname " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.id = ?", PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                Department dep = new Department();
                dep.setId(rs.getInt("DepartmentId"));
                dep.setName(rs.getString("DepName"));

                Seller seller = new Seller();

                seller.setId(rs.getInt("Id"));
                seller.setName(rs.getString("Name"));
                seller.setEmail(rs.getString("Email"));
                seller.setBirthDate(rs.getDate("BirthDate"));
                seller.setBaseSalary(rs.getDouble("BaseSalary"));
                seller.setDepartment(dep);

                return seller;
            }
            return null;

        } catch (SQLException e) {
            throw new Dbexception(e.getMessage());
        }
        finally {

        }


    }

    @Override
    public List<Seller> findAll()
    {
        return List.of();
    }
}
