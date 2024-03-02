package controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import models.Ecole;
import models.Professeur;
import services.EcoleService;

import java.awt.*;
import java.sql.SQLException;
import java.util.Optional;

public class EcoleController {


    public Label nomFX;
    public Label adresseFX;
    public Label nb_professeurs;


    public void setData(Ecole ecole){
        EcoleService es=new EcoleService();
        int nb=es.countProfesseursByEcole(ecole);
        System.out.println(nb);
        nomFX.setText(ecole.getNomEcole());
        adresseFX.setText(ecole.getAdresse());
        nb_professeurs.setText(String.valueOf(nb));
    }

    public void deleteEcole(ActionEvent actionEvent) {
        EcoleService ecoleService=new EcoleService();
        String nom = nomFX.getText();
        Ecole ecole = ecoleService.getEcoleByNom(nom);
            if (ecole != null) {
                // Create a confirmation dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Deletion");
                alert.setHeaderText("Delete Student");
                alert.setContentText("Are you sure you want to delete " + ecole.getNom() + " ?");

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
                        ecoleService.supprimer(ecole.getId());

                        // Remove the selected student from the TableView
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
    }

