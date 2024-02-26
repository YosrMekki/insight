package GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Formation;
import services.FormationService;
import java.util.Optional;


import java.sql.SQLException;
import java.util.List;

public class AddTraining {

    @FXML
    private Button Add_training;

    @FXML
    private Button Available_trainig;

    @FXML
    private Button Dashboard;

    @FXML
    private TextField DomaineTextfield;

    @FXML
    private Button ImportImage;

    @FXML
    private TextField montantTextField;

    @FXML
    private TextField nomTextField;
    @FXML
    private TextField idTextField;

    @FXML
    private TextField search;

    @FXML
    private Label signout;
    @FXML
    private TableColumn<Formation, Integer> idFX;

    private final FormationService formationService=new FormationService();

    @FXML
    private TableView<Formation> tableformation;


    @FXML
    private TableColumn<Formation, String> nomFX;

    @FXML
    private TableColumn<Formation, String> domaineFX;


    @FXML
    private TableColumn<Formation, Double> montantFX;

    @FXML
    void Afficher(ActionEvent event) {
        idFX.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomFX.setCellValueFactory(new PropertyValueFactory<>("nom"));
        domaineFX.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        montantFX.setCellValueFactory(new PropertyValueFactory<>("montant"));

        try {
            List<Formation> formationListList = formationService.getFormationList();
            ObservableList<Formation> formations = FXCollections.observableArrayList(formationListList);
            tableformation.setItems(formations);
            System.out.println(formationListList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        idFX.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomFX.setCellValueFactory(new PropertyValueFactory<>("nom"));
        domaineFX.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        montantFX.setCellValueFactory(new PropertyValueFactory<>("montant"));

        try {
            displayFormation();
        } catch (SQLException e) {
            e.printStackTrace(); // Vous pouvez gérer cette exception de manière plus appropriée selon votre cas d'utilisation
        }
    }

    private void displayFormation() throws SQLException {
        List<Formation> formations = formationService.recuperer();
        ObservableList<Formation> formationsObservable = FXCollections.observableArrayList(formations);

        // Ne créez pas une nouvelle instance de TableView
        // formationtable = new TableView<Formation>();

        // Utilisez plutôt la TableView existante et mettez à jour ses éléments
        tableformation.setItems(formationsObservable);
    }



    @FXML

    void Ajouter(ActionEvent event) {
        // Récupération des valeurs des champs
        String nom = nomTextField.getText();
        String domaine = DomaineTextfield.getText();
        String montantText = montantTextField.getText();

        // Vérification que tous les champs sont remplis
        if (nom.isEmpty() || domaine.isEmpty() || montantText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Veuillez remplir tous les champs !");
            alert.show();
            return; // Arrête l'exécution de la méthode si un champ est vide
        }

        // Vérification du format du montant
        double montant;
        try {
            montant = Double.parseDouble(montantText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Le montant doit être un nombre valide !");
            alert.show();
            return; // Arrête l'exécution de la méthode si le montant n'est pas un nombre valide
        }

        // Création de l'objet Formation et du service de formation
        Formation formation = new Formation(nom, domaine, montant);
        FormationService formationService = new FormationService();

        try {
            // Ajout de la formation via le service
            formationService.ajouter(formation);
            // Affichage d'un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La formation a été ajoutée avec succès !");
            alert.show();
        } catch (SQLException e) {
            // En cas d'erreur lors de l'ajout, affichage d'un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors de l'ajout de la formation : " + e.getMessage());
            alert.show();
        }
    }
    @FXML

    void modifier(ActionEvent event) {
        // Récupérer la formation sélectionnée dans la TableView
        Formation formationSelectionnee = tableformation.getSelectionModel().getSelectedItem();

        if (formationSelectionnee != null) {
            // Afficher une boîte de dialogue ou un formulaire pour permettre à l'utilisateur de saisir les nouvelles informations
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Modification de la formation");
            dialog.setHeaderText(null);
            dialog.setContentText("Entrez le nouveau nom de la formation:");

            // Récupérer la saisie de l'utilisateur
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(nouveauNom -> {
                // Mettre à jour le nom de la formation
                formationSelectionnee.setNom(nouveauNom);

                // Mettre à jour la formation dans la base de données ou dans votre service
                try {
                    formationService.modifier(formationSelectionnee);

                    // Mettre à jour la TableView pour refléter les modifications
                    tableformation.refresh();

                    // Afficher un message de succès à l'utilisateur
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La formation a été modifiée avec succès !");
                    alert.show();
                } catch (SQLException e) {
                    // En cas d'erreur lors de la modification de la formation, afficher un message d'erreur
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Une erreur s'est produite lors de la modification de la formation : " + e.getMessage());
                    alert.show();
                }
            });
        } else {
            // Si aucune formation n'est sélectionnée, afficher un message d'avertissement à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner une formation à modifier.");
            alert.show();
        }
    }
    @FXML
    void supprimer(ActionEvent event) {
        // Récupérer l'identifiant de la formation à supprimer
        int formationId = Integer.parseInt(idTextField.getText());

        // Créer une instance de FormationService
        FormationService formationService = new FormationService();

        try {
            // Appeler la méthode de service pour supprimer la formation
            formationService.supprimer(formationId);

            // Afficher un message de succès à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("La formation a été supprimée avec succès !");
            alert.show();
        } catch (SQLException e) {
            // En cas d'erreur lors de la suppression, afficher un message d'erreur à l'utilisateur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Une erreur s'est produite lors de la suppression de la formation : " + e.getMessage());
            alert.show();
        }
    }}

