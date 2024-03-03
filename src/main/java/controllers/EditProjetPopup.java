package controllers;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import entities.Projet;
import services.ProjetService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class EditProjetPopup extends Dialog<Pair<Projet, String>> {

    private TextField nomProjetField;
    private TextField descriptionField;
    private TextField nomEntrepriseField;
    private TextField domaineField;
    private TextField emailField;


    ProjetService projetService = new ProjetService();
    public EditProjetPopup(Projet initialProjet) {
        setTitle("Edit Projet");

        // Set the button types (Save and Cancel)
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        // Create the grid pane layout for the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        // Add text fields for editing Professor information
        nomProjetField = new TextField(initialProjet.getNomProjet());
        descriptionField = new TextField(initialProjet.getDescription());
        nomEntrepriseField = new TextField(initialProjet.getNomEntreprise());
        domaineField = new TextField(initialProjet.getDomaine());
        emailField = new TextField(initialProjet.getEmail());
        // Add labels and text fields to the grid
        grid.add(new Label("Nom du projet:"), 0, 0);
        grid.add(nomProjetField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Nom de l'entreprise:"), 0, 2);
        grid.add(nomEntrepriseField, 1, 2);
        grid.add(new Label("Domaine:"), 0, 3);
        grid.add(domaineField, 1, 3);
        grid.add(new Label("E-mail:"), 0, 4);
        grid.add(emailField, 1, 4);


        // Set the dialog content
        getDialogPane().setContent(grid);

        // Convert the result to a pair when the Save button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                // Retrieve values from fields
                String nomProjet = nomProjetField.getText();
                String description = descriptionField.getText();
                String nomEntreprise = nomEntrepriseField.getText();
                String domaine = domaineField.getText();
                String email = emailField.getText();

                // Create updated student object
                Projet updatedProjet = new Projet(
                        nomProjet,
                        description,
                        nomEntreprise,
                        domaine,
                        email
                );

                // Return the updated student and email pair
                return new Pair<>(updatedProjet, nomProjet);
            }
            return null;
        });
    }


    private boolean isValidEmail(String email) {
        // Regular expression for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    // Method to check if email is unique




    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}