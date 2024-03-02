package models;

public class Entreprise {
    private int id;
    private String nom;
    private String adresse;
    private int numTel;

    public Entreprise() {
    }

    public Entreprise(int id, String nom, String adresse, int numTel) {
        this.id = id;
        this.nom = nom;
        this.adresse = adresse;
        this.numTel = numTel;
    }
    public Entreprise(String nom, String adresse, int numTel) {
        this.nom = nom;
        this.adresse = adresse;
        this.numTel = numTel;
    }

    // Getters et setters
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getNumTel() {
        return numTel;
    }

    public void setNumTel(int numTel) {
        this.numTel = numTel;
    }

    // Méthode toString pour afficher les informations de l'entreprise
    @Override
    public String toString() {
        return "Entreprise{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", numTel=" + numTel +
                '}';
    }
}
