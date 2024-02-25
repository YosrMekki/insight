package controles;

import entities.Admin;
import entities.Professor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AdminService;
import services.ProfessorService;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddProfessorPopup {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date;

    @FXML
    private TextField emailTextfield;

    @FXML
    private TextField lastNameTextfield;

    @FXML
    private TextField cinTextfield;

    @FXML
    private TextField phoneNumberTextfield;

    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private TextField firstNameTextfield;
    ProfessorService professorService = new ProfessorService();
    public void initProfessorService(ProfessorService service) {
        this.professorService = professorService;
    }

    @FXML

    void addProfessor(ActionEvent event) {
        String email = emailTextfield.getText();
        String password = passwordTextfield.getText();
        String firstName = firstNameTextfield.getText();
        String lastName = lastNameTextfield.getText();
        LocalDate birthDate = date.getValue();
        String phoneNumberString = phoneNumberTextfield.getText();
        String cinString = cinTextfield.getText();

        // Validate email
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Email invalide", "Veuillez entrer une adresse mail valide.");
            return;
        }
        try {
            if (!professorService.isEmailUnique(email)){
                showAlert(Alert.AlertType.ERROR,"Email dupliquée", "Cette adresse mail existe déjà.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Validate password
        if (!isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Mot de passe invalide", "La longueur du mot de passe doit être d’au moins 8 lettres et contenir au moins un chiffre, une lettre majuscule et une lettre minuscule.");
            return;
        }

        // Validate birth date
        if (birthDate == null || !isValidBirthDate(java.sql.Date.valueOf(birthDate))) {
            showAlert(Alert.AlertType.ERROR, "Date de naissance invalide", "Veuillez entrer une date de naissance valide.");
            return;
        }

        // Validate phone number
        if (!isValidPhoneNumber(phoneNumberString)) {
            showAlert(Alert.AlertType.ERROR, "Numéro de téléphone invalide", "Veuillez entrer un numéro de téléphone valide.");
            return;
        }

        // Validate cin
        if (!isValidPhoneNumber(cinString)) {
            showAlert(Alert.AlertType.ERROR, "CIN invalide ", "Veuillez entrer un CIN valide.");
            return;
        }

        // Convert phone number and cin to integer
        int phoneNumber = Integer.parseInt(phoneNumberString);
        int cin = Integer.parseInt(cinString);

        // Create Professor object
        Professor professor = new Professor(email, password, firstName, lastName, java.sql.Date.valueOf(birthDate), phoneNumber, cin);

        try {
            professorService.add(professor);
            // close the popup
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
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
