package services;

import models.Ecole;
import utils.Mydatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EcoleService implements IService<Ecole> {

    private Connection connexion;

    public EcoleService() {
        this.connexion = Mydatabase.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Ecole ecole) throws SQLException {
        String req = "insert into ecole (nom, adresse, nb_professeur) "
                + "values('" + ecole.getNomEcole() + "','"
                + ecole.getAdresse() + "',"
                + ecole.getNb_professeur() + ")";

        Statement statement = connexion.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public void modifier(Ecole ecole) throws SQLException {

        String sql = "update ecole set id= ?, nom = ?, adresse = ?,nb_professeur = ?";
        PreparedStatement preparedStatement = connexion.prepareStatement(sql);
        preparedStatement.setInt(1, ecole.getId());
        preparedStatement.setString(2, ecole.getNomEcole());
        preparedStatement.setString(3, ecole.getAdresse());
        preparedStatement.setInt(4, ecole.getNb_professeur());

        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM ecole WHERE id=?";
        PreparedStatement preparedStatement = connexion.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Ecole> recuperer() throws SQLException {
        List<Ecole> ecole=new ArrayList<>();
        String req = "SELECT * FROM ecole";

        try (PreparedStatement preparedStatement = connexion.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id"); // Assuming there's an 'id' column
                int nb_professeur = resultSet.getInt("nb_professeur");
                String nom = resultSet.getString("nom");
                String adresse = resultSet.getString("adresse");
                ecole.add(new Ecole(id,nom,adresse,nb_professeur));

                System.out.println("ID: " + id + ", nom: " + nom + ", adresse: " + adresse + ", nb_professeur: " + nb_professeur);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
        return ecole;
    }
    public boolean isNomEcoleExist(String nom) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {

                    String query = "SELECT COUNT(*) FROM ecole WHERE nom = ?";
            preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setString(1, nom);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }

        }

        return false;
    }


    public List<String> getNomsEcoles() {
        List<String> nomsEcoles = new ArrayList<>();

        // Connexion à la base de données
            // Requête SQL pour récupérer les noms des écoles
            String query = "SELECT nom FROM ecole";
            try (PreparedStatement statement = connexion.prepareStatement(query)) {
                // Exécution de la requête
                try (ResultSet resultSet = statement.executeQuery()) {
                    // Parcourir les résultats et ajouter les noms d'écoles à la liste
                    while (resultSet.next()) {
                        String nomEcole = resultSet.getString("nom");
                        nomsEcoles.add(nomEcole);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }



        return nomsEcoles;
    }
}


