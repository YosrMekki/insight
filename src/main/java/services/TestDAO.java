package services;

import entities.Question;
import entities.Test;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {
    private Connection connection;
    private QuestionDAO questionDAO;

    public TestDAO(Connection connection) {
        this.connection = connection;
        questionDAO = new QuestionDAO(connection);
    }

    public TestDAO() {

    }

    public List<Test> getAllTestsWithQuestions() {
        List<Test> tests = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM tests";
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(selectAllSQL)) {
            while (resultSet.next()) {
                Test test = new Test(
                        resultSet.getInt("test_id"),
                        resultSet.getInt("duree"),
                        resultSet.getInt("note"),
                        resultSet.getString("matiere")
                );

                // Retrieve questions for the current test
                List<Question> questions = questionDAO.obtenirQuestionsParTestId(test.getTestId());
                test.setQuestions(questions);

                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }


    /* Créez la table si elle n'existe pas encore
    private void createTableIfNotExists() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS tests (" +
                "test_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "duree INTEGER," +
                "note INTEGER," +
                "matiere TEXT)";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        }
    }*/

    // Opération de création (Create)
    public void ajouterTest(Test test) {
        String insertSQL = "INSERT INTO tests (duree, note, matiere) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, test.getDuree());
            pstmt.setInt(2, test.getNote());
            pstmt.setString(3, test.getMatiere());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedTestId = generatedKeys.getInt(1);
                test.setTestId(generatedTestId);
            }

            // Ajouter les questions associées au test
            for (Question question : test.getQuestions()) {
                question.setTestId(test.getTestId());
                questionDAO.ajouterQuestion(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Opération de lecture (Read)
    public Test obtenirTestParId(int testId) {
        String selectSQL = "SELECT * FROM tests WHERE test_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setInt(1, testId);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Test test = new Test(resultSet.getInt("test_id"),
                        resultSet.getInt("duree"),
                        resultSet.getInt("note"),
                        resultSet.getString("matiere"));

                // Obtenir les questions associées et les associer au test
                List<Question> questions = questionDAO.obtenirQuestionsParTestId(testId);
                test.setQuestions(questions);

                return test;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void mettreAJourTest(Test test) {
        String updateSQL = "UPDATE tests SET duree=?, note=?, matiere=? WHERE test_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setInt(1, test.getDuree());
            pstmt.setInt(2, test.getNote());
            pstmt.setString(3, test.getMatiere());
            pstmt.setInt(4, test.getTestId());
            pstmt.executeUpdate();

            // Mettre à jour les questions associées au test
            for (Question question : test.getQuestions()) {
                questionDAO.mettreAJourQuestion(question);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerTest(int testId) {
        String deleteSQL = "DELETE FROM tests WHERE test_id=?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, testId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Test> getAllTests() {
        List<Test> tests = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM tests";
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(selectAllSQL)) {
            while (resultSet.next()) {
                Test test = new Test(
                        resultSet.getInt("test_id"),
                        resultSet.getInt("duree"),
                        resultSet.getInt("note"),
                        resultSet.getString("matiere")
                );
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }
}
