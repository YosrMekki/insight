package services;

import entities.Admin;
import entities.Student;
import entities.User;
import utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements IService<Student>{
    private Connection connection ;
    public StudentService(){
    connection = DB.getInstance().getConnection();}
    @Override
    public void add(Student student) throws SQLException {
        String request ="insert into Student ( email,password,firstName,lastName,birthDate,phoneNumber,cin,role)"+
                "values('"+ student.getEmail()+
                "','"+student.getPassword()+"','"+student.getFirstName()+
                "','"+student.getLastName()+"','"+student.getBirthDate()+"',"+
                student.getPhoneNumber()+","+student.getCin()+",'"+student.getRole()+"')"
                ;
        Statement statement = connection.createStatement();
        statement.executeUpdate(request);
    }

    @Override
    public void update(Student student) throws SQLException {
        String request = "UPDATE student SET email=?, password=?, firstName=?, lastName=?, birthDate=?, phoneNumber=?, cin=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        preparedStatement.setString(1, student.getEmail());
        preparedStatement.setString(2, student.getPassword());
        preparedStatement.setString(3, student.getFirstName());
        preparedStatement.setString(4, student.getLastName());
        preparedStatement.setDate(5, new java.sql.Date(student.getBirthDate().getTime())); // Convert java.util.Date to java.sql.Date
        preparedStatement.setInt(6, student.getPhoneNumber());
        preparedStatement.setInt(7, student.getCin());
        preparedStatement.setInt(8, student.getId()); // Set the ID for the WHERE clause
        preparedStatement.executeUpdate();
    }

    @Override
    public void delete(Student student) throws SQLException {
        String request = "DELETE FROM student WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        preparedStatement.setInt(1, student.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Student> Display() throws SQLException {
        List<Student> studentList = new ArrayList<>();
        String request = "SELECT * FROM student";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(request);
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            Date birthDate = resultSet.getDate("birthDate");
            int phoneNumber = resultSet.getInt("phoneNumber");
            int cin = resultSet.getInt("cin");
            String role = resultSet.getString("role");
            Student student = new Student(id,email,password,firstName,lastName,birthDate,phoneNumber,cin);
            studentList.add(student);
        }
        return studentList;
    }



    public User signIn(String email, String password) throws SQLException {
        // Query the admin table
        String adminQuery = "SELECT * FROM admin WHERE email = ? AND password = ?";
        try (PreparedStatement adminStatement = connection.prepareStatement(adminQuery)) {
            adminStatement.setString(1, email);
            adminStatement.setString(2, password);
            try (ResultSet adminResultSet = adminStatement.executeQuery()) {
                if (adminResultSet.next()) {
                    // Admin found, return Admin object
                    String id = adminResultSet.getString("id");
                    String firstName = adminResultSet.getString("firstName");
                    String lastName = adminResultSet.getString("lastName");
                    Date birthDate = adminResultSet.getDate("birthDate");
                    int phoneNumber = adminResultSet.getInt("phoneNumber");
                    int cin = adminResultSet.getInt("cin");

                    // Set other admin properties as needed
                    return new Admin( email, password, firstName, lastName,birthDate,phoneNumber,cin);
                }
            }
        }

        // Query the student table
        String studentQuery = "SELECT * FROM student WHERE email = ? AND password = ?";
        try (PreparedStatement studentStatement = connection.prepareStatement(studentQuery)) {
            studentStatement.setString(1, email);
            studentStatement.setString(2, password);
            try (ResultSet studentResultSet = studentStatement.executeQuery()) {
                if (studentResultSet.next()) {
                    // Student found, return Student object
                    String id = studentResultSet.getString("id");
                    String firstName = studentResultSet.getString("firstName");
                    String lastName = studentResultSet.getString("lastName");
                    Date birthDate = studentResultSet.getDate("birthDate");
                    int phoneNumber = studentResultSet.getInt("phoneNumber");
                    int cin = studentResultSet.getInt("cin");
                    // Set other student properties as needed
                    return new Student(email, password, firstName, lastName,birthDate,phoneNumber,cin);
                }
            }
        }

        // No user found, return null
        return null;
    }





    public Student getStudentByEmail(String email) throws SQLException {
        String query = "SELECT * FROM student WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String id = resultSet.getString("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            Date birthDate = resultSet.getDate("birthDate");
            int phoneNumber = resultSet.getInt("phoneNumber");
            int cin = resultSet.getInt("cin");
            // Create and return a Student object
            return new Student( email, password, firstName, lastName, birthDate, phoneNumber, cin);
        }

        // Return null if student not found with the given email
        return null;
    }


}
