package services;

import entities.Question;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {
    private final Connection connection;

    public QuestionDAO(Connection connection) {
        this.connection = connection;
        try {
            createTableIfNotExists();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Créez la table si elle n'existe pas encore
    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS questions (" +
                "question_id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                "test_id INTEGER," +
                "question_text TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }

    }

    // Opération de création (Create)
    public void ajouterQuestion(Question question) {
        String insertSQL = "INSERT INTO questions (test_id, question_text) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setInt(1, question.getTestId());
            pstmt.setString(2, question.getQuestionText());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Opération de lecture (Read)
    public Question obtenirQuestionParId(int questionId) {
        String selectSQL = "SELECT * FROM questions WHERE question_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, questionId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return new Question(resultSet.getInt("question_id"),
                        resultSet.getInt("test_id"),
                        resultSet.getString("question_text"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Opération de mise à jour (Update)
    public void mettreAJourQuestion(Question question) {
        String updateSQL = "UPDATE questions SET test_id=?, question_text=? WHERE question_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(1, question.getTestId());
            pstmt.setString(2, question.getQuestionText());
            pstmt.setInt(3, question.getQuestionId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Opération de suppression (Delete)
    public void supprimerQuestion(int questionId) {
        String deleteSQL = "DELETE FROM questions WHERE question_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, questionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir toutes les questions d'un test
    public List<Question> obtenirQuestionsParTestId(int testId) {
        List<Question> questions = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM questions WHERE test_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectAllSQL)) {
            pstmt.setInt(1, testId);
            ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                Question question = new Question(resultSet.getInt("question_id"),
                        resultSet.getInt("test_id"),
                        resultSet.getString("question_text"));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

        // Opération de lecture (Read) pour Question par ID
        public Object getQuestionById(int questionId) {
            String selectSQL = "SELECT * FROM questions WHERE question_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
                pstmt.setInt(1, questionId);
                ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    return new Question(
                            resultSet.getInt("question_id"),
                            resultSet.getInt("test_id"),
                            resultSet.getString("question_text")
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

