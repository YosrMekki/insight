package controles;

import entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import services.StudentService;

import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

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
    private TextField passwordTextfield ;
    private Student student = new Student();

private StudentService studentService = new StudentService();



    public void saveButtonClicked(ActionEvent event) {
        // Update student object with new information
        student.setFirstName(prenomTextfield.getText());
        student.setLastName(nomTextfield.getText());
        // Get selected date from DatePicker
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

    public void setStudent(Student student) {
        this.student = student;
        System.out.println(this.student);
        // Initialize text fields with student information
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
