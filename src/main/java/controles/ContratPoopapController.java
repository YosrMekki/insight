package controles;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import models.Contrat;
import services.ContratService;
import services.ProfService;

import java.sql.Date;
import java.time.LocalDate;

public class ContratPoopapController extends Dialog<Pair<Contrat, Integer>> {

    private TextField dureeField;


    ContratService contratService = new ContratService() ;
    public ContratPoopapController(Contrat initialContrat) {
        setTitle("Renouvellement Contrat");

        // Set the button types (Save and Cancel)
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        // Create the grid pane layout for the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        // Add text fields for editing Professor information
        dureeField = new TextField(String.valueOf(initialContrat.getNb_days()));

        // Add labels and text fields to the grid
        grid.add(new Label("nouvelle durée de contrat:"), 0, 0);
        grid.add(dureeField, 1, 0);


        // Set the dialog content
        getDialogPane().setContent(grid);

        // Convert the result to a pair when the Save button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                // Retrieve values from fields
                int nb_days =Integer.parseInt(dureeField.getText());

                // Create updated student object
                Contrat updatedContrat = new Contrat(
                        nb_days
                );

                // Return the updated student and email pair
                return new Pair<>(updatedContrat, nb_days);
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


    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one digit, one uppercase letter, and one lowercase letter
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(passwordRegex);
    }


    //check phone number
    private boolean isValidPhoneNumber(String phoneNumberString) {
        // Check if the phone number string is not null and has a length of 8
        if (phoneNumberString != null && phoneNumberString.length() == 8) {
            // Check if the phone number string contains only numeric digits
            for (int i = 0; i < phoneNumberString.length(); i++) {
                if (!Character.isDigit(phoneNumberString.charAt(i))) {
                    return false; // Return false if a non-digit character is found
                }
            }
            return true; // Return true if all characters are digits and length is 8
        }
        return false; // Return false if length is not 8 or phone number is null
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

