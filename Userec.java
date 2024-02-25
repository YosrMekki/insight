package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import models.reclamation;
import services.reclamationService;

public class Userec {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField emailrec;

    @FXML
    private TextField fullname;

    @FXML
    private TextField message;

    @FXML
    private TextField pnrec;

    @FXML
    private TextField subject;

    @FXML
    void ajouterec(ActionEvent event) throws SQLException {
        String fullName = fullname.getText();
        String emailrecs = emailrec.getText();
        String pnrecs = pnrec.getText();
        String subjects = subject.getText();
        String messages = message.getText();

        if (isEmptyOrNull(fullName) || isEmptyOrNull(emailrecs) || isEmptyOrNull(pnrecs) || isEmptyOrNull(subjects) || isEmptyOrNull(messages)) {
            showAlert("Please fill in all fields.");
            return;
        }

        if (!isValidPhoneNumber(pnrecs)) {
            showAlert("Invalid phone number format; please enter 8 digits.");
            return;
        }

        if (!isValidEmail(emailrecs)) {
            showAlert("Invalid email format; please enter a valid email address.");
            return;
        }

        if (isExistingReclamation(fullName, emailrecs, Integer.parseInt(pnrecs), subjects, messages)) {
            showAlert("A reclamation with the same details already exists.");
            return;
        }

        reclamation rec = new reclamation(fullName, emailrecs, Integer.parseInt(pnrecs), subjects, messages);

        reclamationService recs = new reclamationService();
        try {
            recs.ajoutereclamtion(rec);
            showAlert("Reclamation added successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{8}");
    }

    private boolean isValidEmail(String email) {
        return email.matches(".+@.+\\..+");
    }

    private boolean isExistingReclamation(String fullName, String emailrec, int pnrec, String subject, String message) throws SQLException {
        reclamationService recs = new reclamationService();
        List<reclamation> allReclamations = recs.readAll();
        for (reclamation existingRec : allReclamations) {
            if (existingRec.getFullname().equals(fullName)
                    && existingRec.getEmailrec().equals(emailrec)
                    && existingRec.getPnrec() == pnrec
                    && existingRec.getSubject().equals(subject)
                    && existingRec.getMessage().equals(message)) {
                return true;
            }
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    @FXML
    void initialize() {
    }
}
