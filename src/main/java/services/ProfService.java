package services;

import models.Ecole;
import models.Professeur;
import utils.Mydatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfService implements IService<Professeur>{
    private Connection connexion;

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
        String sql="select * from professeur";
        Statement statement=connexion.createStatement();
        ResultSet rs=statement.executeQuery(sql);
        List<Professeur> list= new ArrayList<>();
        while (rs.next()){
            Professeur p =new Professeur();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setPrenom(rs.getString("prenom"));
            p.setNum_tel(rs.getInt("num_tel"));
            p.setAdresse(rs.getString("adresse"));
            p.setEcole(null);

        }
        return list;
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

}
