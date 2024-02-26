package models;

public class Formation {
    private int id;
    private String nom, domaine;
    private double montant;

    public Formation() {
    }

    public Formation(String nom, String domaine, double montant) {
        this.nom = nom;
        this.domaine = domaine;
        this.montant = montant;
    }

    public Formation(int id, String nom, String domaine, double montant) {
        this.id = id;
        this.nom = nom;
        this.domaine = domaine;
        this.montant = montant;
    }



    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDomaine() {
        return domaine;
    }

    public double getMontant() {
        return montant;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    @Override
    public String toString() {
        return "Formation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", domaine='" + domaine + '\'' +
                ", montant=" + montant +
                '}';
    }
}