package controles;


import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import models.Professeur;
import services.ProfService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class EditProfesseurPopup extends Dialog<Pair<Professeur, String>> {

    private TextField firstNameField;
    private TextField lastNameField;
    private TextField adresseField;
    private TextField phoneNumberField;

    ProfService profService = new ProfService() ;
    public EditProfesseurPopup(Professeur initialProfessor) {
        setTitle("Edit Professor");

        // Set the button types (Save and Cancel)
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        // Create the grid pane layout for the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        // Add text fields for editing Professor information
        firstNameField = new TextField(initialProfessor.getNom());
        lastNameField = new TextField(initialProfessor.getPrenom());
        adresseField = new TextField(initialProfessor.getAdresse());
        phoneNumberField = new TextField(String.valueOf(initialProfessor.getNum_tel()));

        // Add labels and text fields to the grid
        grid.add(new Label("Prénom:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Cin:"), 0, 3);
        grid.add(adresseField, 1, 3);
        grid.add(new Label("Numéro de téléphone:"), 0, 4);
        grid.add(phoneNumberField, 1, 4);


        // Set the dialog content
        getDialogPane().setContent(grid);

        // Convert the result to a pair when the Save button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                // Retrieve values from fields
                String nom = firstNameField.getText();
                String prenom = lastNameField.getText();
                String adresse = adresseField.getText();
                String num_tel = phoneNumberField.getText();

                // Validate email



                // Validate password (not applicable in this context)





                // Validate CIN (not implemented yet)

                // Create updated student object
                Professeur updatedProfessor = new Professeur(
                        nom,
                        prenom,
                        adresse,
                        Integer.parseInt(num_tel)
                );

                // Return the updated student and email pair
                return new Pair<>(updatedProfessor, nom);
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

    // Method to validate birth date
    private boolean isValidBirthDate(Date birthDate) {
        // Define acceptable range of birth dates (e.g., from 1900 to current year)
        LocalDate minDate = LocalDate.of(1900, 1, 1);
        LocalDate maxDate = LocalDate.now();

        // Convert java.sql.Date to LocalDate
        LocalDate date = birthDate.toLocalDate();

        // Check if birth date is within the acceptable range
        return !(date.isBefore(minDate) || date.isAfter(maxDate));
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
