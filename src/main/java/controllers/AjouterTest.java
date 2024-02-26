package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entities.Quiz;
import entities.Test;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.QuizDAO;
import services.TestDAO;

public class AjouterTest {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField dureeField1;

    @FXML
    private TextField matiereField1;

    @FXML
    private TextField noteField1;

    @FXML
    void AjouterTest(ActionEvent event) {
        Test test = new Test(dureeField1.getText(),matiereField1.getText(),noteField1.getText());
        TestDAO testDAO = new TestDAO();
        testDAO.ajouterTest(test);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("succés d'ajout!");
        alert.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/TesInfo.fxml")) ;
        try {
            Parent parent = loader.load();
            TestInfo testInfo =loader.getController();
            testInfo.setDureetextfield(dureeField1.getText());
            testInfo.setMatieretextfield(matiereField1.getText());
            testInfo.setNotetextfield(noteField1.getText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() {

    }

}
