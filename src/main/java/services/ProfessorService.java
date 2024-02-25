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
}
