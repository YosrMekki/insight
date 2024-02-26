package models;

import java.util.List;

public class Ecole{
    private int id;
    private String nom;
    private String adresse;
    private int nb_professeur;
    private List<Professeur> professeurs;

    public Ecole( String nom, String adresse ,int nb_professeur) {

        this.nom = nom;
        this.adresse = adresse;
        this.nb_professeur = nb_professeur;

    }

    public Ecole(int id, String nom, String adresse, int nb_professeur) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.nb_professeur = nb_professeur;
    }

    public String getNomEcole() {
        return nom;
    }

    public void setNomEcole(String nomEcole) {
        this.nom = nomEcole;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNb_professeur() {
        return nb_professeur;
    }

    public void setNb_professeur(int nb_professeur) {
        this.nb_professeur = nb_professeur;
    }

    public List<Professeur> getProfesseurs() {
        return professeurs;
    }

    public void setProfesseurs(List<Professeur> professeurs) {
        this.professeurs = professeurs;
    }

    @Override
    public String toString() {
        return "Ecole{" +
                "id=" + id +
                ", nomEcole='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", professeurs=" + professeurs +
                '}';
    }
}
