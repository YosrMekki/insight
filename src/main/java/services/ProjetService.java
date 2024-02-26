package services;

import entities.Projet;
import utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetService implements IService<Projet> {
    private Connection connection;

    public ProjetService() {
        connection = MyDataBase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Projet projet) throws SQLException {
        String req = "INSERT INTO `projet`(`nomProjet`, `description`, `nomEntreprise`)" +
                "VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, projet.getNomProjet());
            preparedStatement.setString(2, projet.getDescription());
            preparedStatement.setString(3, projet.getNomEntreprise());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void modifier(Projet projet) throws SQLException {
        String req = "UPDATE projet SET nomProjet=?, description=?, nomEntreprise=? WHERE idProjet=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, projet.getNomProjet());
            preparedStatement.setString(2, projet.getDescription());
            preparedStatement.setString(3, projet.getNomEntreprise());
            preparedStatement.setInt(4, projet.getIdProjet());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM `projet` WHERE idProjet=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Projet> recuperer() throws SQLException {
        String req = "SELECT * FROM projet";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(req)) {

            List<Projet> list = new ArrayList<>();
            while (rs.next()) {
                Projet projet = new Projet();
                projet.setIdProjet(rs.getInt("idProjet"));
                projet.setNomProjet(rs.getString("nomProjet"));
                projet.setDescription(rs.getString("description"));
                projet.setNomEntreprise(rs.getString("nomEntreprise"));
                list.add(projet);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}

