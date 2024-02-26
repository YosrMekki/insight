package easyquiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class QuizController {

    @FXML
    public Label question;

    @FXML
    public Button opt1, opt2, opt3, opt4;

    static int counter = 0;
    static int correct = 0;
    static int wrong = 0;

    @FXML
    private void initialize() {
        loadQuestions();
    }

    private void loadQuestions() {

        if (counter == 0) { //Question 1
            question.setText("1.Quelle est la syntaxe correcte pour afficher 'Hello World' en Java ?");
            opt1.setText("Console. WriteLine (\"Hello World\");");
            opt2.setText("System.out.printin(\"Hello World\");");
            opt3.setText("echo(\"Hello World\");");
            opt4.setText("print (\"Hello World\");");
        }
        if (counter == 1) { //Question 2
            question.setText("2. Comment insérer des COMMENTAIRES dans le code Java ?");
            opt1.setText("//commentaire");
            opt2.setText("#commentaire");
            opt3.setText("*Commentaire*");
            opt4.setText("[Commentaire]");
        }
        if (counter == 2) { //Question 3
            question.setText("3. Quel type de données est utilisé pour créer une variable qui doit stocker du texte ?");
            opt1.setText("varchar");
            opt2.setText("String");
            opt3.setText("txt");
            opt4.setText("text");
        }
        if (counter == 3) { //Question 4
            question.setText("4. Comment créez une variable avec la valeur numérique 5 ?");
            opt1.setText("int x = 5;");
            opt2.setText("boolean x = 5;");
            opt3.setText("num x = 5;");
            opt4.setText("number x =5;");
        }
        if (counter == 4) {//Question 5
            question.setText("5. Comment créez-vous une variable avec le nombre à virgule flottante 2.8 ?");
            opt1.setText("float x =2.8f;");
            opt2.setText("int x =2.8f;");
            opt3.setText("number x =2.8f;");
            opt4.setText(" x =2.8f;");
        }
        if (counter == 5) { //Question 6
            question.setText("6. Quelle méthode peut être utilisée pour trouver la longueur d'une chaîne de caractères ?");
            opt1.setText("length();");
            opt2.setText(" taille();");
            opt3.setText("size();");
            opt4.setText("textSize();");
        }
        if (counter == 6) { //Question 7
            question.setText("7. Quelle méthode peut être utilisée pour renvoyer une chaîne en lettres majuscules ?");
            opt1.setText("TouUpperCase();");
            opt2.setText("uppercase();");
            opt3.setText("Maj();");
                opt4.setText("Tuc();");
        }
        if (counter == 7) { //Question 8
            question.setText("8.comment on peut créer une methode en Java?");
            opt1.setText("methode();");
            opt2.setText("(methode);)");
            opt3.setText("methode[];");
            opt4.setText("methode.");
        }
        if (counter == 8) { //Question 9
            question.setText("Quel opérateur est utilisé pour multiplier des nombres ?");
            opt1.setText("*");
            opt2.setText("+");
            opt3.setText("X");
            opt4.setText("&");
        }
        if (counter == 9) { //Question 10
            question.setText("10. Quel mot-clé est utilisé pour importer un package de la bibliothèque API Java ?");
            opt1.setText("add");
            opt2.setText("Import");
            opt3.setText("lib");
            opt4.setText("getlib");
        }

    }


    @FXML
    public void opt1clicked(ActionEvent event) {
        checkAnswer(opt1.getText().toString());
        if (checkAnswer(opt1.getText().toString())) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
        if (counter == 9) {
            try {
                System.out.println(correct);
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                thisstage.close();
                FXMLLoader quiz = new FXMLLoader(getClass().getResource("result.fxml"));
                Scene quizscene = new Scene(quiz.load());
                quizscene.setFill(Color.TRANSPARENT);
                Stage quizstage = new Stage();
                quizstage.setScene(quizscene);
                quizstage.initStyle(StageStyle.TRANSPARENT);
                quizstage.show();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }

    }

    boolean checkAnswer(String answer) {

        if (counter == 0) {
            if (answer.equals("System.out.printin(\"Hello World\");")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 1) {
            if (answer.equals("//commentaire")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 2) {
            if (answer.equals("String")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 3) {
            if (answer.equals("int x = 5;")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 4) {
            if (answer.equals("float x =2.8f;")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 5) {
            if (answer.equals("length();")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 6) {
            if (answer.equals("TouUpperCase();")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 7) {
            if (answer.equals("methode();")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 8) {
            if (answer.equals("*")) {
                return true;
            } else {
                return false;
            }
        }
        if (counter == 9) {
            if (answer.equals("Import")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @FXML
    public void opt2clicked(ActionEvent event) {
        checkAnswer(opt2.getText().toString());
        if (checkAnswer(opt2.getText().toString())) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
        if (counter == 9) {
            try {
                System.out.println(correct);
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                thisstage.close();
                FXMLLoader quiz = new FXMLLoader(getClass().getResource("result.fxml"));
                Scene quizscene = new Scene(quiz.load());
                quizscene.setFill(Color.TRANSPARENT);
                Stage quizstage = new Stage();
                quizstage.setScene(quizscene);
                quizstage.initStyle(StageStyle.TRANSPARENT);
                quizstage.show();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        checkAnswer(opt3.getText().toString());
        if (checkAnswer(opt3.getText().toString())) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
        if (counter == 9) {
            try {
                System.out.println(correct);
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                thisstage.close();
                FXMLLoader quiz = new FXMLLoader(getClass().getResource("result.fxml"));
                Scene quizscene = new Scene(quiz.load());
                quizscene.setFill(Color.TRANSPARENT);
                Stage quizstage = new Stage();
                quizstage.setScene(quizscene);
                quizstage.initStyle(StageStyle.TRANSPARENT);
                quizstage.show();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    @FXML
    public void opt4clicked(ActionEvent event) {
        checkAnswer(opt4.getText().toString());
        if (checkAnswer(opt4.getText().toString())) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
        if (counter == 9) {
            try {
                System.out.println(correct);
                Stage thisstage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                thisstage.close();
                FXMLLoader quiz = new FXMLLoader(getClass().getResource("result.fxml"));
                Scene quizscene = new Scene(quiz.load());
                quizscene.setFill(Color.TRANSPARENT);
                Stage quizstage = new Stage();
                quizstage.setScene(quizscene);
                quizstage.initStyle(StageStyle.TRANSPARENT);
                quizstage.show();
            } catch(IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

}

