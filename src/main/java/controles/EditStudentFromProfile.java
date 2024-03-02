package controles;

import entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.StudentService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditStudentFromProfile {
    @FXML
    private TextField nomTextfield;
    @FXML
    private TextField prenomTextfield;
    @FXML
    private DatePicker date;
    @FXML
    private TextField numTelTextfield;
    @FXML
    private TextField numCinTextfield;
    @FXML
    private TextField emailTextfield;
    @FXML
    private TextField passwordTextfield;

    private Student student = new Student();
    private StudentService studentService = new StudentService();

    public void saveButtonClicked(ActionEvent event) {
        // Validate input fields
        if (!isValidInput()) {
            return;
        }

        // Update student object with new information
        student.setFirstName(prenomTextfield.getText());
        student.setLastName(nomTextfield.getText());
        Date birthDate = java.sql.Date.valueOf(date.getValue());
        student.setBirthDate(birthDate);
        student.setPhoneNumber(Integer.parseInt(numTelTextfield.getText()));
        student.setCin(Integer.parseInt(numCinTextfield.getText()));
        student.setEmail(emailTextfield.getText());
        student.setPassword(passwordTextfield.getText());

        // Call update method from StudentService
        try {
            studentService.update(student);
            // Close the popup window
            Stage stage = (Stage) nomTextfield.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    private boolean isValidInput() {
        // Validate first name
        if (prenomTextfield.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter first name");
            return false;
        }

        // Validate last name
        if (nomTextfield.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter last name");
            return false;
        }

        // Validate birth date
        if (date.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select birth date");
            return false;
        }

        // Validate phone number
        String phoneNumberString = numTelTextfield.getText();
        if (!isValidPhoneNumber(phoneNumberString)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid phone number");
            return false;
        }

        // Validate CIN
        String cinString = numCinTextfield.getText();
        if (!isValidPhoneNumber(cinString)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid CIN");
            return false;
        }

        // Validate email
        String email = emailTextfield.getText();
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid email address");
            return false;
        }

        // Validate password
        String password = passwordTextfield.getText();
        if (!isValidPassword(password)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid password");
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

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

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$";
        return password.matches(passwordRegex);
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setStudent(Student student) {
        this.student = student;
        nomTextfield.setText(student.getLastName());
        prenomTextfield.setText(student.getFirstName());
        LocalDate birthDateLocalDate = ((java.sql.Date) student.getBirthDate()).toLocalDate();
        date.setValue(birthDateLocalDate);
        numTelTextfield.setText(String.valueOf(student.getPhoneNumber()));
        numCinTextfield.setText(String.valueOf(student.getCin()));
        emailTextfield.setText(student.getEmail());
        passwordTextfield.setText(student.getPassword());
    }
}
