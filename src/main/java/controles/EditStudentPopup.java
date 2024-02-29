package controles;

import entities.Student;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

public class EditStudentPopup extends Dialog<Pair<Student, String>> {

    private TextField firstNameField;
    private TextField lastNameField;
    private DatePicker birthDateField;
    private TextField cinField;
    private TextField phoneNumberField;
    private TextField emailField;

    public EditStudentPopup(Student initialStudent) {
        setTitle("Edit Student");

        // Set the button types (Save and Cancel)
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        // Create the grid pane layout for the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Extract year, month, and day from initialStudent's birth date
        Date initialBirthDate = (Date) initialStudent.getBirthDate();
        java.util.Date initialUtilDate = new java.util.Date(initialBirthDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initialUtilDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create LocalDate object
        LocalDate localDate = LocalDate.of(year, month, day);

        // Add text fields for editing student information
        firstNameField = new TextField(initialStudent.getFirstName());
        lastNameField = new TextField(initialStudent.getLastName());
        birthDateField = new DatePicker(localDate);
        cinField = new TextField(String.valueOf(initialStudent.getCin()));
        phoneNumberField = new TextField(String.valueOf(initialStudent.getPhoneNumber()));
        emailField = new TextField(initialStudent.getEmail());


        // Add labels and text fields to the grid
        grid.add(new Label("Prénom:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Date de naissance:"), 0, 2);
        grid.add(birthDateField, 1, 2);
        grid.add(new Label("Cin:"), 0, 3);
        grid.add(cinField, 1, 3);
        grid.add(new Label("Numéro de téléphone:"), 0, 4);
        grid.add(phoneNumberField, 1, 4);
        grid.add(new Label("Email:"), 0, 5);
        grid.add(emailField, 1, 5);

        // Set the dialog content
        getDialogPane().setContent(grid);

        // Convert the result to a pair when the Save button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                // Retrieve values from fields
                String email = emailField.getText();
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                LocalDate birthDate = birthDateField.getValue();
                String phoneNumberString = phoneNumberField.getText();
                String cinString = cinField.getText();

                // Validate email
                if (!isValidEmail(email)) {
                    showAlert(Alert.AlertType.ERROR, "Email invalide", "Veuillez entrer une adresse mail valide.");
                    return null;
                }

                // Validate password (not applicable in this context)

                // Validate birth date
                if (!isValidBirthDate(Date.valueOf(birthDate))) {
                    showAlert(Alert.AlertType.ERROR, "Date de naissance invalide", "Veuillez entrer une date de naissance valide.");
                    return null;
                }

                // Validate phone number
                if (!isValidPhoneNumber(phoneNumberString)) {
                    showAlert(Alert.AlertType.ERROR, "Numéro de téléphone invalide", "Veuillez entrer un numéro de téléphone valide.");
                    return null;
                }

                if (!isValidPhoneNumber(cinString)){
                    showAlert(Alert.AlertType.ERROR,"cin invalide","Veuillez entrer un cin valide");
                }

                // Create updated student object
                Student updatedStudent = new Student(
                        email,
                        null, // Password not provided here
                        firstName,
                        lastName,
                        Date.valueOf(birthDate),
                        Integer.parseInt(phoneNumberString),
                        Integer.parseInt(cinString)
                );

                // Return the updated student and email pair
                return new Pair<>(updatedStudent, email);
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
