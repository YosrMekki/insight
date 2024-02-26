package entities;

import java.util.ArrayList;
import java.util.List;

public class Test {
    private int testId;
    private int duree;
    private int note;
    private String matiere;
    private List<Question> questions;

    public Test(int testId, int duree, int note, String matiere) {
        this.testId = testId;
        this.duree = duree;
        this.note = note;
        this.matiere = matiere;
        this.questions = new ArrayList<>();
    }

    public Test(String text, String text1, String text2) {
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getMatiere() {
        return matiere;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    @Override
    public String toString() {
        return "Test{" +
                "testId=" + testId +
                ", duree=" + duree +
                ", note=" + note +
                ", matiere='" + matiere + '\'' +
                ", questions=" + questions +
                '}';
    }
}
