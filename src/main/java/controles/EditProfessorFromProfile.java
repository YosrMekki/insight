package controles;

import entities.Professor;
import entities.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ProfessorService;
import services.StudentService;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class EditProfessorFromProfile {
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
    private Professor professor = new Professor();

    private ProfessorService professorService = new ProfessorService();



    public void saveButtonClicked(ActionEvent event) {
        // Update proffesor object with new information
        professor.setFirstName(prenomTextfield.getText());
        professor.setLastName(nomTextfield.getText());
        // Get selected date from DatePicker
        Date birthDate = java.sql.Date.valueOf(date.getValue());
        professor.setBirthDate(birthDate);
        professor.setPhoneNumber(Integer.parseInt(numTelTextfield.getText()));
        professor.setCin(Integer.parseInt(numCinTextfield.getText()));
        professor.setEmail(emailTextfield.getText());
        professor.setPassword(passwordTextfield.getText());

        // Call update method from ProfessorService
        try {
            professorService.update(professor);
            // Close the popup window
            Stage stage = (Stage) nomTextfield.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        }
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
        System.out.println(this.professor);
        // Initialize text fields with student information
        nomTextfield.setText(professor.getLastName());
        prenomTextfield.setText(professor.getFirstName());
        LocalDate birthDateLocalDate = ((java.sql.Date) professor.getBirthDate()).toLocalDate();
        date.setValue(birthDateLocalDate);

        numTelTextfield.setText(String.valueOf(professor.getPhoneNumber()));
        numCinTextfield.setText(String.valueOf(professor.getCin()));
        emailTextfield.setText(professor.getEmail());
        passwordTextfield.setText(professor.getPassword());
    }
}
