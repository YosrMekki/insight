package services;
import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Mydatabase;



import models.Formation;
import java.util.ArrayList;
import java.util.List;
public class FormationService implements IService<Formation>{
    private Connection connection;
    public FormationService() {
        connection = Mydatabase.getInstance().getConnection();
    }

        @Override
    public void ajouter(Formation formation) throws SQLException {
        String req = "INSERT INTO Formation(nom, domaine,montant) VALUES ('" + formation.getNom()+ "','" + formation.getDomaine()+ "','" + formation.getMontant() + "')";
        Statement statement=connection.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public void modifier(Formation formation) throws SQLException {
        String sql = "UPDATE formation SET nom=?, domaine=?, montant=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, formation.getNom());
        preparedStatement.setString(2, formation.getDomaine());
        preparedStatement.setDouble(3, formation.getMontant());
        preparedStatement.setInt(4, formation.getId());

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req="DELETE FROM `formation` WHERE id=?";
        PreparedStatement preparedStatement=connection.prepareStatement(req);
        preparedStatement.setInt(1,id);
        preparedStatement.executeUpdate();

    }

    @Override
    public List<Formation> recuperer() throws SQLException {
        String sql = "select * from formation";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        List<Formation> list = new ArrayList<>();
        while (rs.next()) {
            Formation F= new Formation();
            F.setId(rs.getInt("id"));
            F.setNom(rs.getString("NOM"));
            F.setDomaine(rs.getString("domaine"));
            F.setMontant(rs.getDouble("montant"));
            list.add(F);

        }
        return list;

    }

    public ObservableList<Formation> getFormationList() throws SQLException {

        ObservableList<Formation> formationlist = FXCollections.observableArrayList();

        List <Formation> fl = new ArrayList<>();
        Statement stm = connection.createStatement();
        String query = "select * from formation";
        ResultSet rs;
        rs = stm.executeQuery(query);
        while (rs.next()) {
            Formation r = new Formation();
            r.setId(rs.getInt("id"));
            r.setNom(rs.getString("nom"));
            r.setDomaine(rs.getString("domaine"));
            r.setMontant(rs.getInt("montant"));


            formationlist.add(r);
        }
        return formationlist;

    }


}


