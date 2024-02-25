package utils;
import java.sql.*;
public class nadDB {
   Connection connection;
   final String URL = "jdbc:mysql://localhost:3306/insight";
   final String USER = "root";
   final String PASS = "";
private static nadDB instance ;
    private nadDB() {
        try {
            connection = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("connected");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static nadDB getInstance() {
        if ( instance == null)
        instance = new nadDB();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
