package services;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilis.Mydatabase;

import java.io.IOException;
import java.sql.*;

public class QuizDAO {

    private final Connection connection;
    @FXML
    public Label question;

    @FXML
    public Button opt1, opt2, opt3, opt4;

    static int counter = 0;
    static int correct = 0;
    static int wrong = 0;
    private String correctOptionForCurrentQuestion; // Declare as an instance variable
    private int quizId;

    public QuizDAO() {

        connection = null;
    }

    @FXML
    private void initialize() {
        loadQuestions();
    }
    public QuizDAO(Connection connection) {
        this.connection = connection;
    }

    // Create operation: Add a new quiz to the database
    public void addQuizToDatabase(String quizName) {
        try {
            String insertQuery = "INSERT INTO quizzes (quiz_name) VALUES (?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, PreparedStatement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, quizName);
                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("Creating quiz failed, no rows affected.");
                }

                // Retrieve the generated quiz ID
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        quizId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Creating quiz failed, no ID obtained.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read operation: Load questions from the database based on the quiz ID
    private void loadQuestionsFromDatabase() {
        try {
            String query = "SELECT * FROM questions WHERE quiz_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, quizId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        // Fetch question details and set them in the UI
                        question.setText(resultSet.getString("question_text"));
                        opt1.setText(resultSet.getString("opt1"));
                        opt2.setText(resultSet.getString("opt2"));
                        opt3.setText(resultSet.getString("opt3"));
                        opt4.setText(resultSet.getString("opt4"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update operation: Update the quiz name in the database
    public void updateQuizNameInDatabase(String updatedQuizName) {
        try {
            String updateQuery = "UPDATE quizzes SET quiz_name = ? WHERE quiz_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, updatedQuizName);
                preparedStatement.setInt(2, quizId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete operation: Delete the quiz and associated questions from the database
    public void deleteQuizFromDatabase() {
        try {
            // Delete associated questions first
            String deleteQuestionsQuery = "DELETE FROM questions WHERE quiz_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuestionsQuery)) {
                preparedStatement.setInt(1, quizId);
                preparedStatement.executeUpdate();
            }

            // Then delete the quiz
            String deleteQuizQuery = "DELETE FROM quizzes WHERE quiz_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuizQuery)) {
                preparedStatement.setInt(1, quizId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    private void loadQuestions() {

        try (Connection connection = Mydatabase.getInstance().getconnection()) {
            // Query to retrieve question and options from the database
            String query = "SELECT question_text, option_text, correct_option FROM questions " +
                    "JOIN options ON questions.question_id = options.question_id " +
                    "WHERE questions.question_id = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, counter + 1); // Assuming your question_id starts from 1

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        question.setText(resultSet.getString("question_text"));
                        opt1.setText(resultSet.getString("option_text"));
                        if (resultSet.next()) {
                            opt2.setText(resultSet.getString("option_text"));
                        }
                        if (resultSet.next()) {
                            opt3.setText(resultSet.getString("option_text"));
                        }
                        if (resultSet.next()) {
                            opt4.setText(resultSet.getString("option_text"));
                        }

                        // Set the correct option for the current question
                        correctOptionForCurrentQuestion = resultSet.getString("correct_option");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception based on your application's needs
        }
    }

    @FXML
    public void opt1clicked(ActionEvent event) {
        boolean isCorrect = checkAnswer(opt1.getText().toString(), correctOptionForCurrentQuestion);
        updateScore(isCorrect);
        moveToNextQuestion(event);
    }

    @FXML
    public void opt2clicked(ActionEvent event) {
        boolean isCorrect = checkAnswer(opt2.getText().toString(), correctOptionForCurrentQuestion);
        updateScore(isCorrect);
        moveToNextQuestion(event);
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        boolean isCorrect = checkAnswer(opt3.getText().toString(), correctOptionForCurrentQuestion);
        updateScore(isCorrect);
        moveToNextQuestion(event);
    }

    @FXML
    public void opt4clicked(ActionEvent event) {
        boolean isCorrect = checkAnswer(opt4.getText().toString(), correctOptionForCurrentQuestion);
        updateScore(isCorrect);
        moveToNextQuestion(event);
    }

    private void moveToNextQuestion(ActionEvent event) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            counter++;
            loadQuestions();
        }
    }

    private void updateScore(boolean isCorrect) {
        if (isCorrect) {
            correct = correct + 1;
        } else {
            wrong = wrong + 1;
        }
    }

    boolean checkAnswer(String selectedOption, String correctOption) {
        if (selectedOption.equals(correctOption)) {
            correct = correct + 1;
            return true;
        } else {
            wrong = wrong + 1;
            return false;
        }
    }
}
