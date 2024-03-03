package controllers;

import entities.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ProjetService;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class SujetI implements Initializable {

    @FXML
    private Button devWebButton;

    @FXML
    private Button cyberSecButton;

    @FXML
    private Button buttonIA;

    @FXML
    private TableView<Projet> projetTable;

    @FXML
    private TableColumn<Projet, String> nomProjetColumn;

    @FXML
    private TableColumn<Projet, String> descriptionColumn;

    @FXML
    private TableColumn<Projet, String> nomEntrepriseColumn;

    @FXML
    private TableColumn<Projet, String> domaineColumn;

    @FXML
    private TableColumn<Projet, String> emailColumn;

    @FXML
    private PieChart pieChart;

    private final ProjetService projetService = new ProjetService();
    private ObservableList<Projet> projets;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialise les cell value factories pour chaque colonne
        nomProjetColumn.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        nomEntrepriseColumn.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }


    @FXML
    public void IA(ActionEvent actionEvent) {
        try {
            List<Projet> projetsList = projetService.getProjetByDomaine("Intelligence artificielle");
            projets = FXCollections.observableArrayList(projetsList);
            projetTable.setItems(projets);
            afficherProjetParDomaine("Intelligence artificielle");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void cyberSec(ActionEvent actionEvent) {
        try {
            List<Projet> projetsList = projetService.getProjetByDomaine("Cyber sécurité");
            projets = FXCollections.observableArrayList(projetsList);
            projetTable.setItems(projets);
            afficherProjetParDomaine("Cyber sécurité");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void devWeb(ActionEvent actionEvent) {
        try {
            List<Projet> projetsList = projetService.getProjetByDomaine("Développement web");
            projets = FXCollections.observableArrayList(projetsList);
            projetTable.setItems(projets);
            afficherProjetParDomaine("Développement web");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherProjetParDomaine(String domaine) {
        try {
            // Mettre à jour la liste des projets dans le TableView
            List<Projet> projetsList = projetService.getProjetByDomaine(domaine);
            projets = FXCollections.observableArrayList(projetsList);
            projetTable.setItems(projets);

            // Réafficher les statistiques avec la nouvelle liste de projets
            afficherStatistiques();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherStatistiques() throws SQLException {
        // Récupérer la liste des projets
        List<Projet> projetsList = projetService.recuperer();

        // Compter le nombre de projets par domaine
        Map<String, Integer> statistiques = new HashMap<>();
        for (Projet projet : projetsList) {
            String domaine = projet.getDomaine();
            statistiques.put(domaine, statistiques.getOrDefault(domaine, 0) + 1);
        }

        // Nettoyer la liste des données du PieChart
        pieChart.getData().clear();

        // Ajouter les données au PieChart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : statistiques.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
        pieChart.setData(pieChartData);
    }

}