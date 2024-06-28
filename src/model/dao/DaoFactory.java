package model.dao;

import entities.DB;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory
{
    public static SellerDao createSellerDao()
    {
        return new SellerDaoJDBC(DB.getConnection());
    }



}
