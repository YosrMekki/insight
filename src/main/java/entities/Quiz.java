package entities;

public class Quiz {

    private int Qid;
    private String question;
    private String opt1;
    private String opt2;
    private String opt3;
    private String opt4;
    private String correctOption;

    // Constructors
    public Quiz() {
    }

    public Quiz(String question, String opt1, String opt2, String opt3, String opt4, String correctOption) {
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.correctOption = correctOption;
    }

    // Getters and Setters
    public int getQid() {
        return Qid;
    }

    public void setId(int Qid) {
        this.Qid = Qid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }
}
