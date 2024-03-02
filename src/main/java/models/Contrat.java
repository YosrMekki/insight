package models;

import java.sql.Date;
import java.util.Objects;

public class Contrat {
    private int id;
    private Date date_contrat;
    private int nb_days;
    private Ecole ecole;

    private int ecole_id;

    public Contrat(int id, int nb_days) {
        this.id = id;
        this.nb_days = nb_days;
    }

    public Contrat(int nb_days) {
        this.nb_days = nb_days;
    }
    public Contrat() {
    }

    public Contrat(int nb_days, Ecole ecole) {
       this.ecole=ecole;
       this.nb_days = nb_days;
    }


    public Contrat(int id, Date date_contrat, int nb_days, Ecole ecole) {
        this.id = id;
        this.date_contrat = date_contrat;
        this.nb_days = nb_days;
        this.ecole = ecole;
    }
    public Contrat(int id, Date date_contrat, int nb_days, int ecole_id) {
        this.id = id;
        this.date_contrat = date_contrat;
        this.nb_days = nb_days;
        this.ecole_id = ecole_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public java.sql.Date getDate_contrat() {
        return date_contrat;
    }

    public void setDate_creation(Date date_contrat) {
        this.date_contrat = date_contrat;
    }

    public int getNb_days() {
        return nb_days;
    }

    public void setNb_jours(int nb_days) {
        this.nb_days = nb_days;
    }

    public Ecole getEcole() {
        return ecole;
    }

    public void setEcole(Ecole ecole) {
        this.ecole = ecole;
    }

    public void setDate_contrat(Date date_contrat) {
        this.date_contrat = date_contrat;
    }

    public void setNb_days(int nb_days) {
        this.nb_days = nb_days;
    }

    public int getEcole_id() {
        return ecole_id;
    }

    public void setEcole_id(int ecole_id) {
        this.ecole_id = ecole_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contrat contrat = (Contrat) o;
        return id == contrat.id && nb_days == contrat.nb_days && Objects.equals(date_contrat, contrat.date_contrat) && Objects.equals(ecole, contrat.ecole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date_contrat, nb_days, ecole);
    }

    @Override
    public String toString() {
        return "Contrat{" +
                "id=" + id +
                ", date_creation=" + date_contrat +
                ", nb_jours=" + nb_days +
                ", ecole=" + ecole +
                '}';
    }
}
