package controles;

import entities.Student;
import javafx.fxml.FXML;
import services.StudentService;
import javafx.scene.control.Label;

import java.awt.*;

public class StudentProfile {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label firstNameDisplayLabel;
    @FXML
    private Label lastNameDisplayLabel;
    @FXML
    private Label birthDateDisplayLabel;
    @FXML
    private Label phoneNumberDisplayLabel;
    @FXML
    private Label cinDisplayLabel;
    @FXML
    private Label emailDisplayLabel;
    @FXML
    private Label passwordDisplayLabel;

    StudentService studentService = new StudentService() ;

    public void initialize() {
        // Set action for the "Mon Compte" button

    }
    public void initData(Student student){
        firstNameLabel.setText(student.getFirstName());
        lastNameLabel.setText(student.getLastName());
    }


}
