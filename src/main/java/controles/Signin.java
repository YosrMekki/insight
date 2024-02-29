package controles;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.AdminService;
import services.EmailSenderService;
import services.PasswordResetService;
import services.StudentService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static controles.CodeGenerator.generateRandomCode;

public class Signin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailTextfield;

    @FXML
    private PasswordField passwordTextfield; @FXML
    private Label signupLabel;
    @FXML
    private Label forgotPasswordLabel;
    @FXML
    private Button forgotPassword;
    private final EmailSenderService emailService = new EmailSenderService("smtp.gmail.com","587","sarramadeh@gmail.com","lkej uayo bxes qxdn");
    private final PasswordResetService passwordResetService = new PasswordResetService();
    StudentService studentService = new StudentService();
    @FXML
    private CodeEntry codeEntryController = new CodeEntry();


    @FXML
    void signin(ActionEvent event) {
        String email = emailTextfield.getText();
        String password = passwordTextfield.getText();
        AdminService adminService = new AdminService();
        StudentService studentService = new StudentService();

        try {
            User user = studentService.signIn(email, password);
            if (user != null) {
                if (user instanceof Admin) {
                    // Admin user signed in, allow access to dashboard
                    // Load the dashboard interface
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboard.fxml"));
                    Parent root = loader.load();
                    Dashboard controller = loader.getController();
                    controller.initData(adminService.getAdminByEmail(email).getFirstName(), adminService.getAdminByEmail(email).getLastName());
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else if (user instanceof Student) {
                    // Student user signed in, redirect to studentProfile interface
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/studentProfile.fxml"));
                    Parent root = loader.load();
                    StudentProfile controller = loader.getController();
                    controller.initData(studentService.getStudentByEmail(email));
                    // Pass any necessary data to studentProfile interface
                    // For example: controller.initData(user);
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }
            } else {
                // Display an error message for unauthorized access
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Access Denied");
                alert.setHeaderText(null);
                alert.setContentText("Invalid email or password. Please try again.");
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

    public void sendPasswordResetEmail(String recipientEmail, String username, String password) {
        // Generate a unique token for password reset
        PasswordResetService passwordResetService = new PasswordResetService();
        String token = passwordResetService.generateToken(recipientEmail);

        // Email message details
        String subject = "Password Reset Request";
        String body = "Dear User,\n\nTo reset your password, please click on the following link:\n\nhttp://example.com/reset_password?token=" + token + "\n\nThis link will expire in 1 hour.\n\nBest regards,\nYour App Team";

        // Set up email properties
        // Existing code...

        // Create a session with authentication
        // Existing code...
    }



    @FXML
    void forgotPassword(ActionEvent event) {
        String recipientEmail = emailTextfield.getText();
        try {
            Student student = studentService.getStudentByEmail(recipientEmail);
            System.out.println(student);
            if (student != null) {
                String code = generateRandomCode();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/codeEntry.fxml"));
                Parent root = loader.load();
                CodeEntry codeEntryController = loader.getController();

                // Pass the student object to the Code Entry screen
                codeEntryController.initData(student, code);

                // Send the password reset email
                emailService.sendPasswordResetEmail(recipientEmail, code);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                displayErrorMessage("Student not found with the provided email.");
            }
        } catch (MessagingException e) {
            e.printStackTrace();
            displayErrorMessage("Failed to send password reset email. Please try again later.");
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }




    private void loadCodeEntryFXML(String code) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/codeEntry.fxml"));
            Parent root = loader.load();
            codeEntryController = loader.getController(); // Retrieve the controller instance
            codeEntryController.onCodeGenerated(code); // Set the generated code
            // Optionally, you can set additional properties or data to the controller
            // codeEntryController.setSomeProperty(someValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }







    private void showCodeEntryPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/codeEntry.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Enter Code");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Display an error message dialog
    private void displayErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

