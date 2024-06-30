import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

import java.sql.Connection;
import java.util.Date;

public class Main
{
    public static void main(String[] args)
    {
        Connection conn = null;
        DB.getConnection();

        Department dp = new Department(1,"Books");

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("=== test 1: findById ====");
        Seller seller = sellerDao.findById(3);
        sellerDao.insert(seller);
        System.out.println(seller);


    }
}