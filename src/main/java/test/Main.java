package test;

import services.StudentService;
import utils.DB;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
       // DB db = new DB();

        StudentService s =new StudentService() ;
        System.out.println(s.getStudentByEmail("sarra@pi.com")); ;

    }
}
