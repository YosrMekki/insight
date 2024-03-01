package services;

import entities.Professor;
import entities.Student;
import utils.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorService implements IService<Professor>{
    private Connection connection ;
    public ProfessorService(){
        connection = DB.getInstance().getConnection();
    }
    @Override
    public void add(Professor professor) throws SQLException {
        String request ="insert into professor ( email,password,firstName,lastName,birthDate,phoneNumber,cin,role)"+
                "values('"+ professor.getEmail()+
                "','"+professor.getPassword()+"','"+professor.getFirstName()+
                "','"+professor.getLastName()+"','"+professor.getBirthDate()+"',"+
                professor.getPhoneNumber()+","+professor.getCin()+",'"+professor.getRole()+"')"
                ;
        Statement statement = connection.createStatement();
        statement.executeUpdate(request);
    }

    @Override
    public void update(Professor professor) throws SQLException {
        String request = "UPDATE professor SET email=?, password=?, firstName=?, lastName=?, birthDate=?, phoneNumber=?, cin=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        preparedStatement.setString(1, professor.getEmail());
        preparedStatement.setString(2, professor.getPassword());
        preparedStatement.setString(3, professor.getFirstName());
        preparedStatement.setString(4, professor.getLastName());
        preparedStatement.setDate(5, new java.sql.Date(professor.getBirthDate().getTime())); // Convert java.util.Date to java.sql.Date
        preparedStatement.setInt(6, professor.getPhoneNumber());
        preparedStatement.setInt(7, professor.getCin());
        preparedStatement.setInt(8, professor.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public void delete(Professor professor) throws SQLException {
        String request = "DELETE FROM professor WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(request);
        preparedStatement.setInt(1, professor.getId());
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Professor> Display() throws SQLException {
        List<Professor> professorList = new ArrayList<>();
        String request = "SELECT * FROM professor";
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
            Professor professor = new Professor(id,email,password,firstName,lastName,birthDate,phoneNumber,cin);
            professorList.add(professor);
        }
        return professorList;
    }




    // check email unique

    public boolean isEmailUnique(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM professor WHERE email = ?";
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

    public List<Professor> searchProfessors(String searchTerm) throws SQLException {
        List<Professor> professorList = new ArrayList<>();
        String query = "SELECT * FROM professor WHERE firstName LIKE ? OR lastName LIKE ?";
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
            Professor student = new Professor(id, email, password, firstName, lastName, birthDate, phoneNumber, cin);
            professorList.add(student);
        }
        return professorList;
    }

    public Professor getProfessorByEmail(String email) throws SQLException {
        String query = "SELECT * FROM professor WHERE email = ?";
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
            return new Professor( id,email, password, firstName, lastName, birthDate, phoneNumber, cin);
        }

        // Return null if student not found with the given email
        return null;
    }




}
