package models;

public class Professeur {
    private int id , num_tel;
    private String nom,prenom, adresse;
    private Ecole ecole;

    public Professeur(String prenom,  String nom,  String adresse,int num_tel) {
        this.id = id;
        this.num_tel = num_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }
    public Professeur(int id,String prenom,  String nom,  String adresse,int num_tel) {
        this.id = id;
        this.num_tel = num_tel;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    public Professeur(String nom, String prenom, int num_tel, String adresse, Ecole ecole) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.num_tel = num_tel;
        this.ecole = ecole;
    }
    public Professeur (){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Ecole getEcole() {
        return ecole;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }

    @Override
    public String toString() {
        return "Professeur{" +
                "id=" + id +
                ", num_tel=" + num_tel +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", ecole=" + ecole +
                '}';
    }
}
