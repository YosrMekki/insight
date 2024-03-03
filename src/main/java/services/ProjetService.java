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
        String req = "INSERT INTO `projet`(`nomProjet`, `description`, `nomEntreprise`, `domaine`, `email`)" +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, projet.getNomProjet());
            preparedStatement.setString(2, projet.getDescription());
            preparedStatement.setString(3, projet.getNomEntreprise());
            preparedStatement.setString(4, projet.getDomaine());
            preparedStatement.setString(5, projet.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    @Override
    public void modifier(Projet projet) throws SQLException {
        String req = "UPDATE projet SET nomProjet=?, description=?, nomEntreprise=?, domaine=?, email=? WHERE idProjet=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(req)) {
            preparedStatement.setString(1, projet.getNomProjet());
            preparedStatement.setString(2, projet.getDescription());
            preparedStatement.setString(3, projet.getNomEntreprise());
            preparedStatement.setString(4, projet.getDomaine());
            preparedStatement.setString(5, projet.getEmail());
            preparedStatement.setInt(6, projet.getIdProjet());
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
        String req = "SELECT idProjet, nomProjet, description, nomEntreprise, domaine, email FROM projet";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(req)) {

            List<Projet> list = new ArrayList<>();
            while (rs.next()) {
                Projet projet = new Projet();
                projet.setIdProjet(rs.getInt("idProjet"));
                projet.setNomProjet(rs.getString("nomProjet"));
                projet.setDescription(rs.getString("description"));
                projet.setNomEntreprise(rs.getString("nomEntreprise"));
                projet.setDomaine(rs.getString("domaine"));
                projet.setEmail(rs.getString("email"));
                list.add(projet);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    public List<Projet> getProjetByDomaine(String domaine) throws SQLException {
        List<Projet> projets = new ArrayList<>();


            // Requête SQL pour récupérer les projets par domaine
            String sql = "SELECT * FROM projet WHERE domaine = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, domaine);
                // Exécution de la requête
                try (ResultSet rs = stmt.executeQuery()) {
                    // Parcours des résultats et ajout des projets à la liste
                    while (rs.next()) {
                        String nomProjet = rs.getString("nomProjet");
                        String description = rs.getString("description");
                        String nomEntreprise = rs.getString("nomEntreprise");
                        String email = rs.getString("email");
                        // Création d'un objet Projet et ajout à la liste
                        projets.add(new Projet(nomProjet, description, nomEntreprise, domaine, email));
                    }
                }
            }
        catch (SQLException e) {
            // Gestion des exceptions
            e.printStackTrace();
        }

        System.out.println(projets);
        return projets;
    }

}

