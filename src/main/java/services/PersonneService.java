package services;

import models.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import models.Personne;
import utils.Mydatabase;

public class PersonneService implements IService<Personne> {

    private Connection connexion;

    public PersonneService() {
        this.connexion = Mydatabase.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Personne personne) throws SQLException {
        String req="insert into personne (age, nom, prenom) "
        + "values(" + personne.getAge() + ",'"
        + personne.getPrenom()+"','"+personne.getNom() +"')";

        Statement statement=connexion.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public void modifier(Personne personne) throws SQLException {

        String sql="update personne set id= ?, age = ?, nom = ?, prenom = ?";
        PreparedStatement preparedStatement=connexion.prepareStatement(sql);
        preparedStatement.setInt(1, personne.getId());
        preparedStatement.setInt(2, personne.getAge());
        preparedStatement.setString(3, personne.getNom());
        preparedStatement.setString(4, personne.getPrenom());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM personne WHERE id=?";
        PreparedStatement preparedStatement = connexion.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Personne> recuperer() throws SQLException {
        return null;
    }
}
