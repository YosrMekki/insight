package services;

import entities.Admin;
import entities.Student;
import utils.DB;

import java.sql.*;
import java.util.List;

public class AdminService implements IService<Admin>{
    private Connection connection ;
public AdminService(){
    connection = DB.getInstance().getConnection();
}
    @Override
    public void add(Admin admin) throws SQLException {
        String request ="insert into Admin( email,password,firstName,lastName,birthDate,phoneNumber,cin,role)"+
                "values('"+admin.getEmail()+
                "','"+admin.getPassword()+"','"+admin.getFirstName()+
                "','"+admin.getLastName()+"','"+admin.getBirthDate()+"',"+
                admin.getPhoneNumber()+","+admin.getCin()+",'"+admin.getRole()+"')"
                ;
        Statement statement = connection.createStatement();
        statement.executeUpdate(request);
    }

    @Override
    public void update(Admin admin) throws SQLException {

    }

    @Override
    public void delete(Admin admin) throws SQLException {

    }

    @Override
    public List<Admin> Display() throws SQLException {
        return null;
    }

    public Admin getAdminByEmail(String email) throws SQLException {
        String query = "SELECT * FROM admin WHERE email = ?";
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
            return new Admin( email, password, firstName, lastName, birthDate, phoneNumber, cin);
        }

        // Return null if student not found with the given email
        return null;
    }
}
