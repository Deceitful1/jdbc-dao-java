import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.impl.SellerDaoJDBC;

import java.sql.Connection;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Connection conn = null;
        DB.getConnection();

        Department dp = new Department(1, null);

        SellerDaoJDBC sellerDao = (SellerDaoJDBC) DaoFactory.createSellerDao();
        System.out.println("=== test 1: findById ====");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("=== test2: findByDepartment");

        List<Seller> sellers = sellerDao.findByDepartment(dp);
        for (Seller obj : sellers) {
            System.out.println(obj);
        }
    }
}