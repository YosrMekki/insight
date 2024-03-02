package services;

import models.Ecole;
import models.Professeur;
import utils.Mydatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfService implements IService<Professeur>{
    private Connection connexion;
    private EcoleService es = new EcoleService();

    public ProfService() {
        this.connexion = Mydatabase.getInstance().getConnexion();
    }

    @Override
    public void ajouter(Professeur prof) throws SQLException {
        String req="insert into professeur (nom, prenom, num_tel, adresse, ecole_id) "
                + "values('" + prof.getNom()+"','"
                + prof.getPrenom()+"',"
                + prof.getNum_tel()+ ",'"
                + prof.getAdresse()+"',"
                + prof.getEcole().getId()+")";

        Statement statement=connexion.createStatement();
        statement.executeUpdate(req);
    }

    @Override
    public void modifier(Professeur professeur) throws SQLException {

        String sql="update professeur set nom = ?, prenom = ? ,num_tel= ?, adresse = ?";
        PreparedStatement preparedStatement=connexion.prepareStatement(sql);
        preparedStatement.setString(1, professeur.getNom());
        preparedStatement.setString(2, professeur.getPrenom());
        preparedStatement.setInt(3, professeur.getNum_tel());
        preparedStatement.setString(4, professeur.getAdresse());
        preparedStatement.executeUpdate();
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "DELETE FROM professeur WHERE id=?";
        PreparedStatement preparedStatement = connexion.prepareStatement(req);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @Override
    public List<Professeur> recuperer() throws SQLException {
        EcoleService es=new EcoleService();
        String sql="select * from professeur";
        List<Professeur> professeurs = new ArrayList<>();
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Professeur professeur = new Professeur();
                professeur.setId(rs.getInt("id"));
                professeur.setNom(rs.getString("nom"));
                professeur.setPrenom(rs.getString("prenom"));
                professeur.setNum_tel(rs.getInt("num_tel"));
                professeur.setAdresse(rs.getString("adresse"));
                professeur.setEcole_id(rs.getInt("ecole_id"));

                // Si vous souhaitez également charger l'école à laquelle ce professeur est associé, vous pouvez le faire ici
                // int idEcole = rs.getInt("ecole_id");
                // Ecole ecole = recupererEcoleParId(idEcole); // Méthode à implémenter
                // professeur.setEcole(ecole);
                professeurs.add(professeur);
            }
        }
        return professeurs;
    }

    public List<Professeur> recupererProfParEcoleId(int idEcole) throws SQLException {
        String sql = "SELECT * FROM professeur WHERE ecole_id = ?";
        List<Professeur> professeurs = new ArrayList<>();
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, idEcole);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Professeur professeur = new Professeur();
                professeur.setId(rs.getInt("id"));
                professeur.setNom(rs.getString("nom"));
                professeur.setPrenom(rs.getString("prenom"));
                professeur.setNum_tel(rs.getInt("num_tel"));
                professeur.setAdresse(rs.getString("adresse"));
                // Si vous souhaitez également charger l'école à laquelle ce professeur est associé, vous pouvez le faire ici
                // int idEcole = rs.getInt("ecole_id");
                // Ecole ecole = recupererEcoleParId(idEcole); // Méthode à implémenter
                // professeur.setEcole(ecole);
                professeurs.add(professeur);
            }
        }
        return professeurs;
    }

    public List<Professeur> searchProfesseur(String searchTerm)  throws SQLException {
        List<Professeur> profList = new ArrayList<>();
        String query = "SELECT * FROM professeur WHERE nom LIKE ? OR prenom LIKE ?";
        PreparedStatement statement = connexion.prepareStatement(query);
        statement.setString(1, "%" + searchTerm + "%"); // Utilisez le terme de recherche avec le joker % pour rechercher des correspondances partielles
        statement.setString(2, "%" + searchTerm + "%");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String nom = resultSet.getString("nom");
            String prenom = resultSet.getString("prenom");
            int num_tel = resultSet.getInt("num_tel");
            String adresse = resultSet.getString("adresse");
            int ecole_id = resultSet.getInt("ecole_id");

            Ecole ecole=es.getEcoleById(ecole_id);

           Professeur professeur = new Professeur(id, prenom, nom , adresse,num_tel, ecole_id);
            profList.add(professeur);
        }
        return profList;
    }
}
