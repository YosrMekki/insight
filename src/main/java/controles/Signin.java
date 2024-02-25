package controles;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import entities.Admin;
import entities.Student;
import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.AdminService;
import services.StudentService;

public class Signin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailTextfield;

    @FXML
    private TextField passwordTextfield; @FXML
    private Label signupLabel;


    @FXML
    void signin(ActionEvent event) {
        String email = emailTextfield.getText();
        String password = passwordTextfield.getText();
        AdminService adminService = new AdminService();
        StudentService studentService = new StudentService();

        try {
            User user = studentService.signIn(email, password);
            if (user != null && user instanceof Admin) {
                // Admin user signed in, allow access to dashboard
                // Load the dashboard interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));


                Parent root = loader.load();
                Dashboard controller=loader.getController();
                controller.initData(adminService.getAdminByEmail(email).getFirstName(),adminService.getAdminByEmail(email).getLastName());
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                // Display an error message for unauthorized access
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Access Denied");
                alert.setHeaderText(null);
                alert.setContentText("Only admins are allowed to access the dashboard.");
                alert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            // Handle exceptions
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred");
            alert.showAndWait();
        }
    }
    @FXML
    void goToSignup(javafx.scene.input.MouseEvent event) {
        try {
            // Load the signup screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException
        }
    }

    @FXML
    void initialize() {


    }

}

