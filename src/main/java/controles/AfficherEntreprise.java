package controles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Ecole;
import models.Entreprise;
import models.Professeur;
import services.EntrepriseService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherEntreprise implements Initializable {
    public TableColumn<Entreprise, Integer> idColumn;
    public TableColumn<Entreprise , String> nomColumn;
    public TableColumn<Entreprise , String> adresseColumn;
    public TableColumn<Entreprise , Integer> numTelColumn;
    public TableColumn<Entreprise , Void> actionsColumn;
    public TableView<Entreprise > entrepriseTable;
    @FXML
    private GridPane entrepriseGrid;

    private List<Entreprise> entreprises;
    private  EntrepriseService entrepriseService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        entrepriseService = new EntrepriseService();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        numTelColumn.setCellValueFactory(new PropertyValueFactory<>("numTel"));

        initializeActionsColumn();

        try {
            displayEntreprises();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur
        }
    }

    public void displayEntreprises() throws SQLException {
        List<Entreprise> entreprisesList = entrepriseService.recuperer();
        ObservableList<Entreprise> entreprises = FXCollections.observableArrayList(entreprisesList);
        entrepriseTable.setItems(entreprises);
    }

    private void initializeActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox container = new HBox(editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    handleEditButton();
                });

                deleteButton.setOnAction(event -> {
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

    private void handleEditButton() {
        Entreprise selectedEntreprise = entrepriseTable.getSelectionModel().getSelectedItem();

        if (selectedEntreprise != null) {
            // Create an instance of the EditEntreprisePopup dialog
            EditEntreprisePopup editEntreprisePopup = new EditEntreprisePopup(selectedEntreprise);

            // Show the dialog and wait for the user response
            Optional<Pair<Entreprise, String>> result = editEntreprisePopup.showAndWait();

            // If the user clicked the Save button, update the entreprise information
            result.ifPresent(pair -> {
                Entreprise updatedEntreprise = pair.getKey();
                String newNom = pair.getValue();

                // Update the selected entreprise with the new information
                selectedEntreprise.setNom(updatedEntreprise.getNom());
                selectedEntreprise.setAdresse(updatedEntreprise.getAdresse());
                selectedEntreprise.setNumTel(updatedEntreprise.getNumTel());

                try {
                    // Update the changes in the database using the EntrepriseService
                    entrepriseService.modifier(selectedEntreprise);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }

                // Refresh the TableView to reflect the changes
                entrepriseTable.refresh();
            });
        } else {
            // No entreprise selected, display an error message or handle accordingly
            System.out.println("No entreprise selected for editing.");
        }
    }

    private void handleDeleteButton() {
        Entreprise selectedEntreprise = entrepriseTable.getSelectionModel().getSelectedItem();

        if (selectedEntreprise != null) {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Enterprise");
            alert.setContentText("Are you sure you want to delete " + selectedEntreprise.getNom() + "?");

            // Add OK and Cancel buttons to the dialog
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            // Show the confirmation dialog and wait for user response
            Optional<ButtonType> result = alert.showAndWait();

            // If the user confirms deletion, proceed with deletion
            if (result.isPresent() && result.get() == okButton) {
                try {
                    // Delete the selected entreprise from the database using the EntrepriseService
                    entrepriseService.supprimer(selectedEntreprise.getId());

                    // Remove the selected entreprise from the TableView
                    entrepriseTable.getItems().remove(selectedEntreprise);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // No entreprise selected, display an error message or handle accordingly
            System.out.println("No entreprise selected for deletion.");
        }
    }
}
