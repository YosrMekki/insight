package controles ;

import entities.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.CodeGeneratedCallback;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class CodeEntry implements CodeGeneratedCallback {


    public TextField codeField;
    private String generatedCode; // Variable to store the generated code

    // Method to set the generated code

    Student student ;
    // Method to verify the entered code

    public void initData(Student student, String generatedCode) {
        this.student = student;
        this.generatedCode = generatedCode;
        System.out.println("Student object initialized in CodeEntry controller: " + student);
    }
    public void verifyCode(javafx.event.ActionEvent actionEvent) {
        String enteredCode = codeField.getText();
        System.out.println("Generated Code: " + generatedCode);
        System.out.println("Entered Code: " + enteredCode);
        boolean isValid = enteredCode.equals(generatedCode); // Compare entered code with the generated code
        if (isValid) {
            // Code is valid, perform necessary actions
            System.out.println("Code is valid");
            navigateToResetPasswordScreen();
        } else {
            // Code is invalid, handle accordingly
            System.out.println("Code is invalid");
        }
    }

    // Method to navigate to the reset password screen
    private void navigateToResetPasswordScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resetPassword.fxml"));
            Parent root = loader.load();
            ResetPassword resetPasswordController = loader.getController();

            // Pass the student object to the reset password screen
            resetPasswordController.setStudent(student);

            Scene scene = new Scene(root);
            Stage stage = (Stage) codeField.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException
        }
    }

    @Override
    public void onCodeGenerated(String code) {
        this.generatedCode=code;
    }
}
