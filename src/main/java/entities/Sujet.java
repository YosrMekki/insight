package entities;

public class Sujet {
    private int idSujet;
    private String domaine;

    public Sujet() {}

    public Sujet(int idSujet, String domaine) {
        this.idSujet = idSujet;
        this.domaine = domaine;
    }

    public Sujet(String domaine) {
        this.domaine = domaine;
    }

    public int getIdSujet() {
        return idSujet;
    }

    public void setIdSujet(int idSujet) {
        this.idSujet = idSujet;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    @Override
    public String toString() {
        return "Sujet{" +
                "idSujet=" + idSujet +
                ", domaine='" + domaine + '\'' +
                '}';
    }
}
