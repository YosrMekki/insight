package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entities.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import services.QuizDAO;

public class AjouterQuiz {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField correctOptionField;

    @FXML
    private TextField opt1Field;

    @FXML
    private TextField opt2Field;

    @FXML
    private TextField opt3Field;

    @FXML
    private TextField opt4Field;

    @FXML
    private TextField questionField;

    @FXML
    void AjouterQuiz(ActionEvent event) {
        Quiz quiz = new Quiz(questionField.getText(),opt1Field.getText(),opt2Field.getText(),opt3Field.getText(),opt4Field.getText(),correctOptionField.getText());
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.addQuizToDatabase("Java Quiz");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("succés d'ajout!");
        alert.show();

    }

    @FXML
    void initialize() {

    }

}
