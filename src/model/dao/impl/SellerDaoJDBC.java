package model.dao.impl;

import entities.DB;
import entities.Dbexception;
import entities.Department;
import entities.Seller;
import model.dao.SellerDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        try {
            ps = conn.prepareStatement("INSERT INTO seller(Name,Email,BirthDate,BaseSalary,DepartmentId)" +
                    "VALUES (?,?,?,?,?) ", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new Dbexception("nenhuma linha foi afetada");
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void update(Seller seller)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("""
                    UPDATE seller SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?
                    WHERE Id = ?
                    """, PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setString(1, seller.getName());
            ps.setString(2, seller.getEmail());
            ps.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            ps.setDouble(4, seller.getBaseSalary());
            ps.setInt(5, seller.getDepartment().getId());
            ps.setInt(6, seller.getId());

            ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        }
    }

    @Override
    public void deleteById(Integer id)
    {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM seller WHERE seller.Id = ? ");
            ps.setInt(1,id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                            "WHERE seller.Id = ? ", PreparedStatement.RETURN_GENERATED_KEYS
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

    public List<Seller> findByDepartment(Integer departmentId)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Seller> sellers = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT seller.*, department.Name as DepName " +
                    "from seller INNER JOIN department ON " +
                    "seller.DepartmentId = department.Id WHERE " +
                    "department.Id = ? " +
                    "ORDER BY Id");

            ps.setInt(1, departmentId);
            rs = ps.executeQuery();

            while (rs.next()) {
                sellers.add(instantiateSeller(rs, instantiateDepartment(rs)));
            }
            return sellers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT seller.*,department.Name as Depname FROM " +
                    "seller INNER JOIN department ON " +
                    "seller.DepartmentId = department.Id " +
                    "WHERE seller.Id <> ? ORDER BY Id");
            ps.setInt(1, 0);
            rs = ps.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {
                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }
                Seller sell = instantiateSeller(rs, instantiateDepartment(rs));
                sellers.add(sell);
            }
            return sellers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department)
    {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(
                    "SELECT seller.*, department.Name as Depname " +
                            "FROM seller INNER JOIN department " +
                            "ON seller.DepartmentId = department.Id " +
                            "WHERE seller.DepartmentId = ? " +
                            "ORDER BY NAME", PreparedStatement.RETURN_GENERATED_KEYS
            );
            ps.setInt(1, department.getId());
            rs = ps.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null) {

                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }
            return sellers;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



