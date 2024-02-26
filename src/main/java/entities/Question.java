package entities;

public class Question {
    private int questionId;
    private int testId;
    private String questionText;
    public Question(int questionId, int testId, String questionText) {
        this.questionId = questionId;
        this.testId = testId;
        this.questionText = questionText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getTestId() {
        return testId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", testId=" + testId +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}

