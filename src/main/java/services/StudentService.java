package services;

import entities.*;
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
                    return new Admin( email, password, firstName, lastName, birthDate, phoneNumber, cin);
                }
            }
        }

        // If admin not found, query the student table
        String studentQuery = "SELECT * FROM student WHERE email = ? AND password = ?";
        try (PreparedStatement studentStatement = connection.prepareStatement(studentQuery)) {
            studentStatement.setString(1, email);
            studentStatement.setString(2, password);
            try (ResultSet studentResultSet = studentStatement.executeQuery()) {
                if (studentResultSet.next()) {
                    // Student found, return Student object
                    // Adjust the ResultSet fields based on your student table columns
                    int id = studentResultSet.getInt("id");
                    String firstName = studentResultSet.getString("firstName");
                    String lastName = studentResultSet.getString("lastName");
                    Date birthDate = studentResultSet.getDate("birthDate");
                    int phoneNumber = studentResultSet.getInt("phoneNumber");
                    int cin = studentResultSet.getInt("cin");

                    // Set other student properties as needed
                    return new Student(id, email, password, firstName, lastName, birthDate, phoneNumber, cin);
                }
            }
        }
        String professorQuery = "SELECT * FROM professor WHERE email = ? AND password = ?";
        try (PreparedStatement professorStatement = connection.prepareStatement(professorQuery)) {
            professorStatement.setString(1, email);
            professorStatement.setString(2, password);
            try (ResultSet professorResultSet = professorStatement.executeQuery()) {
                if (professorResultSet.next()) {
                    // Professor found, return Professor object
                    // Adjust the ResultSet fields based on your professor table columns
                    int id = professorResultSet.getInt("id");
                    String firstName = professorResultSet.getString("firstName");
                    String lastName = professorResultSet.getString("lastName");
                    Date birthDate = professorResultSet.getDate("birthDate");
                    int phoneNumber = professorResultSet.getInt("phoneNumber");
                    int cin = professorResultSet.getInt("cin");

                    return new Professor(id, email, password, firstName, lastName,birthDate,phoneNumber,cin);
                }
            }
        }

        // Neither admin nor student no professor found with the provided credentials
        return null;
    }


    public Student getStudentByEmail(String email) throws SQLException {
        String query = "SELECT * FROM student WHERE email = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String password = resultSet.getString("password");
            Date birthDate = resultSet.getDate("birthDate");
            int phoneNumber = resultSet.getInt("phoneNumber");
            int cin = resultSet.getInt("cin");
            // Create and return a Student object
            return new Student( id,email, password, firstName, lastName, birthDate, phoneNumber, cin);
        }

        // Return null if student not found with the given email
        return null;
    }

//controle de saisie
    // check email unique

    public boolean isEmailUnique(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM student WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count == 0; // If count is 0, email is unique
                }
            }
        }

        return false; // Default to false if something went wrong with the query
    }
    public void updatePassword(Student student) throws SQLException {
        String request = "UPDATE student SET password=? WHERE email=?";
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        preparedStatement.setString(1, student.getPassword());
        preparedStatement.setString(2, student.getEmail()); // Assuming ID is the primary key
        preparedStatement.executeUpdate();
    }

    public List<Student> searchStudents(String searchTerm) throws SQLException {
        List<Student> studentList = new ArrayList<>();
        String query = "SELECT * FROM student WHERE firstName LIKE ? OR lastName LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + searchTerm + "%"); // Utilisez le terme de recherche avec le joker % pour rechercher des correspondances partielles
        statement.setString(2, "%" + searchTerm + "%");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            Date birthDate = resultSet.getDate("birthDate");
            int phoneNumber = resultSet.getInt("phoneNumber");
            int cin = resultSet.getInt("cin");
            Student student = new Student(id, email, password, firstName, lastName, birthDate, phoneNumber, cin);
            studentList.add(student);
        }
        return studentList;
    }

    //Note jointure
    public void addNote(int studentId, String noteContent) throws SQLException {
        String query = "INSERT INTO note (studentId, noteContent) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            statement.setString(2, noteContent);
            statement.executeUpdate();
        }
    }
    public List<Note> getNotesByStudentId(int studentId) throws SQLException {
        List<Note> notes = new ArrayList<>();
        String query = "SELECT * FROM note WHERE studentId = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Note note = new Note();
                    note.setId(resultSet.getInt("id"));
                    note.setNoteContent(resultSet.getString("noteContent"));
                    // Add more properties as needed
                    notes.add(note);
                }
            }
        }
        return notes;
    }


    public void updateNoteContent(int noteId, String newNoteContent) throws SQLException {
        String query = "UPDATE note SET noteContent = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newNoteContent);
            statement.setInt(2, noteId);
            statement.executeUpdate();
        }
    }

    public void deleteNoteById(int noteId) throws SQLException {
        String query = "DELETE FROM note WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, noteId);
            statement.executeUpdate();
        }
    }

}
