package controllers;

import entities.Projet;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import services.ProjetService;


import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class EditProjetPopup extends Dialog<Pair<Projet, String>>{

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField adresseField;


    ProjetService projetService = new ProjetService() ;
    public EditProjetPopup(Projet initialProjet) {
        setTitle("Edit Projet");

        // Set the button types (Save and Cancel)
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        // Create the grid pane layout for the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        // Add text fields for editing Professor information
        firstNameField = new TextField(initialProjet.getNomProjet());
        lastNameField = new TextField(initialProjet.getDescription());
        adresseField = new TextField(initialProjet.getNomEntreprise());


        // Add labels and text fields to the grid
        grid.add(new Label("Nom de projet:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Nom de l'entreprise:"), 0, 3);



        // Set the dialog content
        getDialogPane().setContent(grid);

        // Convert the result to a pair when the Save button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                // Retrieve values from fields
                String nomProjet = firstNameField.getText();
                String description = lastNameField.getText();
                String nomEntreprise = adresseField.getText();









                // Create updated project object
                Projet updatedProfessor = new Projet(
                        nomProjet,
                        description,
                        nomEntreprise
                );

                // Return the updated student and email pair
                return new Pair<>(updatedProfessor, nomProjet);
            }
            return null;
        });
    }




    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
