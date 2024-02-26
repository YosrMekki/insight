package services;

import entities.Sujet;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SujetService implements IService<Sujet>{
    private Connection connection;
    public SujetService(){
        connection = MyDataBase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Sujet sujet) throws SQLException {
        String req = "INSERT INTO `sujet`(`domaine`)" +
                "VALUES ('" + sujet.getDomaine() + "')";


        Statement statement = connection.createStatement();
        statement.executeUpdate(req);

    }

    @Override
    public void modifier(Sujet sujet) throws SQLException {
        String req = "update sujet set domaine =?, ";
        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setInt(1,sujet.getIdSujet());
        preparedStatement.setString(2,sujet.getDomaine());

        preparedStatement.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `sujet` WHERE idSujet=?";
        PreparedStatement preparedStatement= connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Sujet> recuperer() throws SQLException {
        String req = "select * from sujet";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        List<Sujet> list = new ArrayList<>();
        while (rs.next()) {
            Sujet s = new Sujet();
            s.setIdSujet(rs.getInt("idSujet"));
            s.setDomaine(rs.getString("domaine"));
            list.add(s);
        }
        return list;
    }

}
