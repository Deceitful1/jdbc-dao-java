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
                throw new RuntimeException(e.getMessage());
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
            throw new RuntimeException(e);
        }
    }
    public static void closeStatement(Statement st)
    {
        try {
            if (!st.isClosed())
            {
                st.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void closeResultSet(ResultSet rs)
    {
        try {
            if (!rs.isClosed())
            {
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public static Properties loadProperties()
    {
        Properties properties = new Properties();

        try (FileInputStream fs = new FileInputStream("C:\\Users\\gfeli\\IdeaProjects\\projeto-jdbc\\src\\db.properties")) {
            properties.load(fs);
            return properties;


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
