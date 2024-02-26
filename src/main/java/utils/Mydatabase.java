package utils;

import java.sql.*;

public class Mydatabase {
    final String URL = "jdbc:mysql://localhost:3306/esprit3a17";
    final String USER="root";
    final String PASS="";
    private Connection connexion;
    private static Mydatabase instance;
    private Mydatabase(){
        try {
            connexion = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("connected");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static Mydatabase getInstance(){
    if(instance == null)
        instance=new Mydatabase();
    return instance;
    }

    public Connection getConnexion() {
        return connexion;
    }
}
