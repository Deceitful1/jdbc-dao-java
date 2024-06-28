package entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB
{
    private static Connection conn = null;

    public static Connection getConnection()
    {
        if (conn == null) {
            Properties prop = loadProperties();
            String url = prop.getProperty("dburl");

            try {
                conn = DriverManager.getConnection(url, prop);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return conn;
    }

    public static void closeConnection()
    {
        try {
            if (!conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            throw new Dbexception(e.getMessage());
        }
    }

    public static void closeStatement(Statement stat)
    {
        try {
            if (!stat.isClosed()) {
                stat.close();
            }
        } catch (SQLException e) {
            throw new Dbexception(e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet rs)
    {
        try {
            if (!rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new Dbexception(e.getMessage());
        }
    }


    public static Properties loadProperties()
    {
        Properties prop = new Properties();
        try (FileInputStream fs = new FileInputStream("C:\\Users\\gfeli\\IdeaProjects\\projeto-jdbc\\src\\entities\\db.properties")) {
            prop.load(fs);
            return prop;

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
