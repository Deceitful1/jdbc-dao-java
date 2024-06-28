import entities.Department;
import entities.Seller;
import model.dao.DaoFactory;
import model.dao.SellerDao;

import java.util.Date;

public class Main
{
    public static void main(String[] args)
    {
        Department dp = new Department(1,"Books");

        Seller seller = new Seller(1,"joseph","joseph@gmail.com",new Date(),1200.00,dp);
        System.out.println(seller);

        SellerDao sellerDao = DaoFactory.createSellerDao();

    }
}