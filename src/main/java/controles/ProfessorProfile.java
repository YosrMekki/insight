package controles;

import entities.Professor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ProfessorService;


import java.io.IOException;
import java.sql.SQLException;

public class ProfessorProfile {

    @FXML
    private Button monCompteButton;
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
    @FXML
    private Label firstNameText;
    @FXML
    private Label lastNameText;
    @FXML
    private Label birthDateText ;
    @FXML
    private Label phoneNumberText ;
    @FXML
    private Label cinText ;
    @FXML
    private Label emailText ;
    @FXML
    private Label passwordText;
    @FXML
    private Button modifierButton ;
    @FXML
    private BorderPane profilePane;
    private boolean isInfoVisible = false;

    ProfessorService professorService = new ProfessorService();
    private Professor professor;

    public void initialize() {
        // Set initial visibility of welcome label

        welcomeLabel.setVisible(true);

        // Hide displayed labels initially
        hideDisplayedLabels();


        // Set action for the "Mon Compte" button
        monCompteButton.setOnAction(event -> {
            if (!isInfoVisible) {
                showDisplayedLabels();
                modifierButton.setVisible(true); // Show the "Modifier Informations" button

            } else {
                hideDisplayedLabels();
                modifierButton.setVisible(false); // hide the "Modifier Informations" button

            }
        });
    }

    public void initData(Professor professor) {
        firstNameLabel.setText(professor.getFirstName());
        lastNameLabel.setText(professor.getLastName());
        firstNameDisplayLabel.setText(professor.getFirstName());
        lastNameDisplayLabel.setText(professor.getLastName());
        birthDateDisplayLabel.setText(professor.getBirthDate().toString());
        phoneNumberDisplayLabel.setText(String.valueOf(professor.getPhoneNumber()));
        cinDisplayLabel.setText(String.valueOf(professor.getCin()));
        emailDisplayLabel.setText(professor.getEmail());
    }

    private void showDisplayedLabels() {
        welcomeLabel.setVisible(true);
        firstNameLabel.setVisible(true);
        lastNameLabel.setVisible(true);
        firstNameDisplayLabel.setVisible(true);
        lastNameDisplayLabel.setVisible(true);
        birthDateDisplayLabel.setVisible(true);
        phoneNumberDisplayLabel.setVisible(true);
        cinDisplayLabel.setVisible(true);
        emailDisplayLabel.setVisible(true);
        firstNameText.setVisible(true);
        lastNameText.setVisible(true);
        birthDateText.setVisible(true);
        phoneNumberText.setVisible(true);
        cinText.setVisible(true);
        emailText.setVisible(true);
        passwordText.setVisible(false);
        profilePane.setVisible(true);
        modifierButton.setVisible(true);

        isInfoVisible = true;
    }

    private void hideDisplayedLabels() {
        //welcomeLabel.setVisible(false);
        //firstNameLabel.setVisible(false);
        //lastNameLabel.setVisible(false);
        firstNameDisplayLabel.setVisible(false);
        lastNameDisplayLabel.setVisible(false);
        birthDateDisplayLabel.setVisible(false);
        phoneNumberDisplayLabel.setVisible(false);
        cinDisplayLabel.setVisible(false);
        emailDisplayLabel.setVisible(false);
        firstNameText.setVisible(false);
        lastNameText.setVisible(false);
        birthDateText.setVisible(false);
        phoneNumberText.setVisible(false);
        cinText.setVisible(false);
        emailText.setVisible(false);
        passwordText.setVisible(false);
        profilePane.setVisible(false);
        modifierButton.setVisible(false);
        isInfoVisible = false;
    }


    public void handleModifierButtonAction(javafx.event.ActionEvent event) {
        try {
            // Load the popup FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editProfessorFromProfile.fxml"));
            Parent root = loader.load();

            // Get the controller
            EditProfessorFromProfile controller = loader.getController();

            // Pass the student information to the popup controller
            try {
                controller.setProfessor(professorService.getProfessorByEmail(String.valueOf(emailDisplayLabel.getText()))); // Assuming 'student' is the current student object
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Create the popup stage
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Modifier Informations");
            popupStage.setScene(new Scene(root));

            // Show the popup
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/signin.fxml"));
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene in the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
