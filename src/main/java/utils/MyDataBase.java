package utils;
import java.sql.*;

public class MyDataBase {
    final String URL="jdbc:mysql://localhost:3306/gestion_des_projet";
    final String USER="root";
    final String PASS="";
    private Connection connection;
    private static MyDataBase instance;
    private MyDataBase(){
        try {
            connection=DriverManager.getConnection(URL, USER, PASS);
            System.out.println("connected");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
  public static MyDataBase getInstance() {
      if(instance== null)
          instance = new MyDataBase();
          return instance;
  }
      public Connection getConnection(){
          return connection;
      }

}
