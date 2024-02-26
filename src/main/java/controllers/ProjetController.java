package controllers;

import entities.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import services.ProjetService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
        if (nomProjet.getText().isEmpty() || descriptionProjet.getText().isEmpty() || nomEntreprise.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs du formulaire.");
            alert.show();
            return;
        }

        Projet projet = new Projet(nomProjet.getText(), descriptionProjet.getText(), nomEntreprise.getText());
        ProjetService projetService = new ProjetService();

        // Récupérer les projets existants
        List<Projet> projets = null;
        try {
            projets = projetService.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Vérifier l'unicité du projet
        boolean projetExiste = false;
        for (Projet projetExistant : projets) {
            if (projetExistant.getNomProjet().equals(projet.getNomProjet()) &&
                    projetExistant.getDescription().equals(projet.getDescription()) &&
                    projetExistant.getNomEntreprise().equals(projet.getNomEntreprise())) {
                projetExiste = true;
                break;
            }
        }

        if (projetExiste) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Un projet avec les mêmes informations existe déjà.");
            alert.show();
        } else {
            // Ajouter le projet à la base de données
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

        initializeActionsColumn();
        try {
            displayProjets();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeActionsColumn() {
        projetActionComumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox container = new HBox(editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    // Handle edit action
                    handleEditButton();
                });

                deleteButton.setOnAction(event -> {
                    // Handle delete action
                    handleDeleteButton();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
    }
    private void handleDeleteButton() {
        Projet selectedProjet = projetTable.getSelectionModel().getSelectedItem();

        if (selectedProjet != null) {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Student");
            alert.setContentText("Are you sure you want to delete " + selectedProjet.getNomProjet() + " " + selectedProjet.getNomEntreprise() + "?");

            // Add OK and Cancel buttons to the dialog
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            // Show the confirmation dialog and wait for user response
            Optional<ButtonType> result = alert.showAndWait();

            // If the user confirms deletion, proceed with deletion
            if (result.isPresent() && result.get() == okButton) {
                try {
                    // Delete the selected student from the database using the StudentService
                    projetService.supprimer(selectedProjet.getIdProjet());

                    // Remove the selected student from the TableView
                    projetTable.getItems().remove(selectedProjet);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }
            }
        } else {
            // No student selected, display an error message or handle accordingly
            System.out.println("No student selected for deletion.");
        }
    }

    private void handleEditButton() {
        Projet selectedProjet = projetTable.getSelectionModel().getSelectedItem();

        if (selectedProjet != null) {
            // Create an instance of the EditStudentPopup dialog
            EditProjetPopup editStudentPopup = new EditProjetPopup(selectedProjet);

            // Show the dialog and wait for the user response
            Optional<Pair<Projet, String>> result = editStudentPopup.showAndWait();

            // If the user clicked the Save button, update the student information
            result.ifPresent(pair -> {
                Projet updatedProjet = pair.getKey();
                String newEmail = pair.getValue();

                // Update the selected student with the new information
                selectedProjet.setNomProjet(updatedProjet.getNomProjet());
                selectedProjet.setDescription(updatedProjet.getDescription());
                selectedProjet.setNomEntreprise(updatedProjet.getNomEntreprise());

                try {
                    // Update the changes in the database using the StudentService
                    projetService.modifier(selectedProjet);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }

                // Refresh the TableView to reflect the changes
                projetTable.refresh();
            });
        } else {
            // No student selected, display an error message or handle accordingly
            System.out.println("No student selected for editing.");
        }
    }

    public void displayProjets() throws SQLException {
        System.out.println("hello");
        List<Projet> projetsList = projetService.recuperer();
        ObservableList<Projet> projets = FXCollections.observableArrayList(projetsList);


        projetTable.setItems(projets);

    }
    }

