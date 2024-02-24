package services;

import entities.Admin;
import utils.DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
}
