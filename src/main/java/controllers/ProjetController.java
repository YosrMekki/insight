package controllers;

import entities.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ProjetService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ProjetController implements Initializable {
    public TableView<Projet> projetTable;
    public TableColumn<Projet, Void> projetActionComumn;
    ProjetService projetService= new ProjetService();
    @FXML
    private TableView<Projet> tableView;

    @FXML
    private TableColumn<Projet, Integer> idProjetTV;

    @FXML
    private TableColumn<Projet, String> nomProjetTV;

    @FXML
    private TableColumn<Projet, String> descriptionTV;

    @FXML
    private TableColumn<Projet, String> nomEntrepriseTV;

    @FXML
    private TextArea descriptionProjet;

    @FXML
    private TextField nomEntreprise;

    @FXML
    private TextField nomProjet;



    @FXML
    void ajouterProjet(ActionEvent event) {
        Projet projet = new Projet(nomProjet.getText(), descriptionProjet.getText(), nomEntreprise.getText());
        ProjetService projetService = new ProjetService();
        try {
            projetService.ajouter(projet);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le projet a été ajouté avec succès");
            alert.show();

            // Rafraîchir le TableView après l'ajout
            displayProjets();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }


    @FXML
    void modifier(ActionEvent event) {
        Projet projet = tableView.getSelectionModel().getSelectedItem();  // Récupérer l'élément sélectionné
        if (projet != null) {
            projet.setNomProjet(nomProjet.getText());
            projet.setDescription(descriptionProjet.getText());
            projet.setNomEntreprise(nomEntreprise.getText());

            ProjetService projetService = new ProjetService();
            try {
                projetService.modifier(projet);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le projet a été modifié avec succès");
                alert.show();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un projet à modifier.");
            alert.show();
        }
    }

    @FXML
    void supprimer(ActionEvent event) {
        Projet projet = tableView.getSelectionModel().getSelectedItem();  // Récupérer l'élément sélectionné
        if (projet != null) {
            ProjetService projetService = new ProjetService();
            try {
                projetService.supprimer(projet.getIdProjet());  // Utiliser l'ID du projet
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le projet a été supprimé avec succès");
                alert.show();

                // Rafraîchir le TableView après la suppression
                displayProjets();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un projet à supprimer.");
            alert.show();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idProjetTV.setCellValueFactory(new PropertyValueFactory<>("idProjet"));
        nomProjetTV.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
        descriptionTV.setCellValueFactory(new PropertyValueFactory<>("description"));
        nomEntrepriseTV.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));

        try {
            displayProjets();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }




    }

    public void displayProjets() throws SQLException {
        System.out.println("hello");
        List<Projet> projetsList = projetService.recuperer();
        ObservableList<Projet> projets = FXCollections.observableArrayList(projetsList);


        projetTable.setItems(projets);

    }
    }

