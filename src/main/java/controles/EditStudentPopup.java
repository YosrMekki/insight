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
        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Birth Date:"), 0, 2);
        grid.add(birthDateField, 1, 2);
        grid.add(new Label("CIN:"), 0, 3);
        grid.add(cinField, 1, 3);
        grid.add(new Label("Phone Number:"), 0, 4);
        grid.add(phoneNumberField, 1, 4);
        grid.add(new Label("Email:"), 0, 5);
        grid.add(emailField, 1, 5);

        // Set the dialog content
        getDialogPane().setContent(grid);

        // Convert the result to a pair when the Save button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                Student updatedStudent = new Student(
                        emailField.getText(),
                        null, // Password not provided here
                        firstNameField.getText(),
                        lastNameField.getText(),
                        Date.valueOf(birthDateField.getValue()),

                        Integer.parseInt(phoneNumberField.getText()),
                        Integer.parseInt(cinField.getText())
                );
                return new Pair<>(updatedStudent, emailField.getText());
            }
            return null;
        });
    }
}
