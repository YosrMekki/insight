package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import entities.Projet;
import services.ProjetService;

import java.sql.SQLException;

public class AjouterProjet {

    public TableView projetTable;
    public TableColumn<Projet, Void> projetActionComumn;
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
            rafraichirTableView();  // Rafraîchir la TableView après l'ajout
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le projet a été ajouté avec succès");
            alert.show();
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
                rafraichirTableView();  // Rafraîchir la TableView après la modification
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
                projetService.supprimer(projet.getIdProjet());
                rafraichirTableView();  // Rafraîchir la TableView après la suppression
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le projet a été supprimé avec succès");
                alert.show();
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

    private void rafraichirTableView() {
        ProjetService projetService = new ProjetService();
        try {
            ObservableList<Projet> projetList = FXCollections.observableArrayList(projetService.recuperer());
            tableView.setItems(projetList);
        } catch (SQLException e) {
            e.printStackTrace();  // Gérer l'exception selon votre besoin
        }
    }

    @FXML
    void initialize() {
        // Assurez-vous d'initialiser la TableView correctement ici (idProjetTV, nomProjetTV, descriptionTV, nomEntrepriseTV)
    }
}
