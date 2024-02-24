package controles;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
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
        Student student = new Student(emailTextfield.getText(),passwordTextfield.getText(),prenomTextfield.getText(),nomTextfield.getText(), Date.valueOf(date.getValue()),Integer.parseInt(numTelTextfield.getText()),Integer.parseInt(numCinTextfield.getText()));
        StudentService studentService = new StudentService();
        try {
            studentService.add(student);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("etudiant ajouté");
            alert.show();
            // Navigate back to the signin interface
            goToSignin(event);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
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

}
