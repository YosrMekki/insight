package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Projet {

    private int idProjet;
    private String nomProjet;
    private String description;
    private String nomEntreprise;
    private String domaine;
    private String email;

    public Projet(int idProjet, String nomProjet, String description, String nomEntreprise, String domaine, String email) {
        this.idProjet = idProjet;
        this.nomProjet = nomProjet;
        this.description = description;
        this.nomEntreprise = nomEntreprise;
        this.domaine = domaine;
        this.email = email;
    }

    public Projet(String nomProjet, String description, String nomEntreprise, String domaine, String email) {
        this.nomProjet = nomProjet;
        this.description = description;
        this.nomEntreprise = nomEntreprise;
        this.domaine = domaine;
        this.email = email;
    }

    public Projet(){}
    public int getIdProjet() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet = idProjet;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet = nomProjet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Projet{" +
                "idProjet=" + idProjet +
                ", nomProjet='" + nomProjet + '\'' +
                ", description='" + description + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                ", domaine='" + domaine + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
