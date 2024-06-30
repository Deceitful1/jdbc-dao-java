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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement("INSERT INTO seller (Name,Email,BirthDate,BaseSalary,DepartmentId " +
                    "VALUES (?,?,?,?,?) ", PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("done!  rows affected: " + rowsAffected);
            }


            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

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
                Department dep = instantiateDepartment(rs);

                Seller seller = instantiateSeller(rs, dep);


                return seller;
            }
            return null;

        } catch (SQLException e) {
            throw new Dbexception(e.getMessage());
        } finally {

        }


    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException
    {
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;

    }

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException
    {
        Seller sell = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"), rs.getDouble("BaseSalary"), dep);
        return sell;
    }

    @Override
    public List<Seller> findAll()
    {
        return List.of();
    }
}
