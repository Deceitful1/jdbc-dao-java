import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.impl.SellerDaoJDBC;

import java.sql.Connection;
import java.util.Date;
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
        seller.setName("loos");
        sellerDao.update(seller);
        sellerDao.deleteById(20);
        sellerDao.insert(new Seller(null,"greg","greg@gmail.com",new Date(),4000.0,dp));
        System.out.println(seller);

        System.out.println("=== test2: findByDepartment");

        List<Seller> sellers = sellerDao.findByDepartment(dp);
        for (Seller obj : sellers) {
            System.out.println(obj);
        }
        System.out.println("------------------------------");
        List<Seller> sellers1 = sellerDao.findAll();
        sellers1.forEach(System.out::println);
    }
}