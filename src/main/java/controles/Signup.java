package controles;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

import entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.StudentService;

public class Signup {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date;

    @FXML
    private TextField emailTextfield;

    @FXML
    private TextField nomTextfield;

    @FXML
    private TextField numCinTextfield;

    @FXML
    private TextField numTelTextfield;

    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private TextField prenomTextfield;

    @FXML
    void ajouterEtudiant(ActionEvent event) {
        // Retrieve input values
        String email = emailTextfield.getText();
        String password = passwordTextfield.getText();
        String firstName = prenomTextfield.getText();
        String lastName = nomTextfield.getText();
        Date birthDate = Date.valueOf(date.getValue());
        String phoneNumberString = numTelTextfield.getText(); // Get phone number as string
        String cinString = numCinTextfield.getText(); // Get cin as string

        // Check if phone number is numeric and has length of 8
        if (!isValidPhoneNumber(phoneNumberString)) {
            showAlert(Alert.AlertType.ERROR, "Numéro de téléphone invalide", "Veuillez entrer un numéro de téléphone valide.");
            return;
        }
        int phoneNumber = Integer.parseInt(phoneNumberString); // Convert to int after validation

        // Check if cin is numeric
        if (!isValidPhoneNumber(cinString)) {
            showAlert(Alert.AlertType.ERROR, "CIN invalide", "Veuillez entrer un CIN valide.");
            return;
        }
        int cin = Integer.parseInt(cinString); // Convert to int after validation

        // email valide check
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Email invalide", "Veuillez entrer une adresse mail valide.");
            return;
        }

        // mot de passe valide check
        if (!isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Mot de passe invalide", "La longueur du mot de passe doit être d’au moins 8 lettres et contenir au moins un chiffre, une lettre majuscule et une lettre minuscule.");
            return;
        }

        // date de naissance check
        if (!isValidBirthDate(birthDate)) {
            showAlert(Alert.AlertType.ERROR, "Date de naissance invalide", "Veuillez entrer une date de naissance valide.");
            return;
        }

        // Check if email is unique
        StudentService studentService = new StudentService();
        try {
            if (!studentService.isEmailUnique(email)) {
                showAlert(Alert.AlertType.ERROR, "Email dupliquée", "Cette adresse mail existe déjà.");
                return;
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while checking email uniqueness.");
            e.printStackTrace();
            return;
        }

        // Create Student object
        Student student = new Student(email, password, firstName, lastName, birthDate, phoneNumber, cin);

        // Add student to database
        try {
            studentService.add(student);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Étudiant ajouté avec succès.");
            // Navigate back to the signin interface
            goToSignin(event);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while adding student to database.");
            e.printStackTrace();
        }
    }

    private void goToSignin(ActionEvent event) {
        try {
            // Load the signin FXML file
            Parent root = FXMLLoader.load(getClass().getResource("/signin.fxml"));

            // Create the scene
            Scene scene = new Scene(root);

            // Get the stage from the event source
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
    @FXML
    void initialize() {
        assert date != null : "fx:id=\"date\" was not injected: check your FXML file 'signup.fxml'.";
        assert emailTextfield != null : "fx:id=\"emailTextfield\" was not injected: check your FXML file 'signup.fxml'.";
        assert nomTextfield != null : "fx:id=\"nomTextfield\" was not injected: check your FXML file 'signup.fxml'.";
        assert numCinTextfield != null : "fx:id=\"numCinTextfield\" was not injected: check your FXML file 'signup.fxml'.";
        assert numTelTextfield != null : "fx:id=\"numTelTextfield\" was not injected: check your FXML file 'signup.fxml'.";
        assert passwordTextfield != null : "fx:id=\"passwordTextfield\" was not injected: check your FXML file 'signup.fxml'.";
        assert prenomTextfield != null : "fx:id=\"prenomTextfield\" was not injected: check your FXML file 'signup.fxml'.";

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
