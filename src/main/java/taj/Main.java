package taj;

import entities.Question;
import entities.Test;
import services.QuestionDAO;
import services.TestDAO;
import utilis.Mydatabase; // Adjust the import statement

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create an instance of MyDatabase or initialize your database connection as needed
        Mydatabase db = Mydatabase.getInstance(); // Use the correct class name

        // Test QuestionDAO
        QuestionDAO questionDAO = new QuestionDAO(db.getconnection());

        // Create a question
        Question question = new Question(1, 3, "what is Getters ad setters used for?");
        questionDAO.ajouterQuestion(question);

        // Retrieve a question by ID
        Question retrievedQuestion = (Question) questionDAO.getQuestionById(1);
        System.out.println("Retrieved Question: " + retrievedQuestion);

        // Update the question (add your update logic here if needed)

        // Retrieve all questions for a test
        List<Question> questionsForTest = questionDAO.obtenirQuestionsParTestId(3); // Use the correct test ID
        System.out.println("Questions for Test 3: " + questionsForTest);

        // Delete the question
        questionDAO.supprimerQuestion(1);

        // Test TestDAO
        TestDAO testDAO = new TestDAO(db.getconnection());
        List<Test> testsWithQuestions = testDAO.getAllTestsWithQuestions();

        for (Test test : testsWithQuestions) {
            System.out.println("Test: " + test);
            System.out.println("Questions: " + test.getQuestions());
            System.out.println("-------------");
        }

        // Create a test
        Test test = new Test(1, 30, 20, "What is a constructor");
        testDAO.ajouterTest(test);

        // Retrieve a test by ID
        Test retrievedTest = testDAO.obtenirTestParId(1);
        System.out.println("Retrieved Test: " + retrievedTest);

        // Update
       /* retrievedTest.setMatiere("Software Engineering");
        testDAO.mettreAJourTest(retrievedTest);*/

        // Retrieve all tests
        List<Test> allTests = testDAO.getAllTests();
        System.out.println("All Tests: " + allTests);

        // Delete the test
        testDAO.supprimerTest(1);

    }
}
