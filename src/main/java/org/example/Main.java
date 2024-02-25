package org.example;

import services.StudentService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {


        StudentService studentService = new StudentService();
        try {
            System.out.println(   studentService.isEmailUnique("sarra@pi.com")
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}