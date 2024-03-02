package services;

import models.Entreprise;
import utils.Mydatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntrepriseService implements IService<Entreprise> {
    private Connection connexion;

    // Constructeur
    public EntrepriseService() {
        this.connexion = Mydatabase.getInstance().getConnexion();;
    }

    @Override
    public void ajouter(Entreprise entreprise) throws SQLException {
        String sql = "INSERT INTO entreprise (nom, adresse, num_tel) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entreprise.getNom());
            preparedStatement.setString(2, entreprise.getAdresse());
            preparedStatement.setInt(3, entreprise.getNumTel());

            preparedStatement.executeUpdate();

            // Récupérer l'ID généré pour l'entreprise ajoutée
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entreprise.setId(resultSet.getInt(1));
            }
        }
    }

    @Override
    public void modifier(Entreprise entreprise) throws SQLException {
        String sql = "UPDATE entreprise SET nom=?, adresse=?, num_tel=? WHERE id=?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
            preparedStatement.setString(1, entreprise.getNom());
            preparedStatement.setString(2, entreprise.getAdresse());
            preparedStatement.setInt(3, entreprise.getNumTel());
            preparedStatement.setInt(4, entreprise.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM entreprise WHERE id=?";
        try (PreparedStatement preparedStatement = connexion.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Entreprise> recuperer() throws SQLException {
        List<Entreprise> entreprises = new ArrayList<>();
        String sql = "SELECT * FROM entreprise";
        try (Statement statement = connexion.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Entreprise entreprise = new Entreprise();
                entreprise.setId(resultSet.getInt("id"));
                entreprise.setNom(resultSet.getString("nom"));
                entreprise.setAdresse(resultSet.getString("adresse"));
                entreprise.setNumTel(resultSet.getInt("num_tel"));
                entreprises.add(entreprise);
            }
        }
        return entreprises;
    }
}
