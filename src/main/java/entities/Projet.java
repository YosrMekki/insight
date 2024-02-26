package entities;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Projet {

    private SimpleIntegerProperty idProjet;
    private SimpleStringProperty nomProjet;
    private SimpleStringProperty description;
    private SimpleStringProperty nomEntreprise;

    public Projet() {
        this.idProjet = new SimpleIntegerProperty();
        this.nomProjet = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.nomEntreprise = new SimpleStringProperty();
    }

    public Projet(int idProjet, String nomProjet, String description, String nomEntreprise) {
        this.idProjet = new SimpleIntegerProperty(idProjet);
        this.nomProjet = new SimpleStringProperty(nomProjet);
        this.description = new SimpleStringProperty(description);
        this.nomEntreprise = new SimpleStringProperty(nomEntreprise);
    }

    public Projet(String nomProjet, String description, String nomEntreprise) {
        this.nomProjet = new SimpleStringProperty(nomProjet);
        this.description = new SimpleStringProperty(description);
        this.nomEntreprise = new SimpleStringProperty(nomEntreprise);
    }

    public int getIdProjet() {
        return idProjet.get();
    }

    public SimpleIntegerProperty idProjetProperty() {
        return idProjet;
    }

    public void setIdProjet(int idProjet) {
        this.idProjet.set(idProjet);
    }

    public String getNomProjet() {
        return nomProjet.get();
    }

    public SimpleStringProperty nomProjetProperty() {
        return nomProjet;
    }

    public void setNomProjet(String nomProjet) {
        this.nomProjet.set(nomProjet);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getNomEntreprise() {
        return nomEntreprise.get();
    }

    public SimpleStringProperty nomEntrepriseProperty() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise.set(nomEntreprise);
    }

    @Override
    public String toString() {
        return "Projet{" +
                "idProjet=" + idProjet +
                ", nomProjet='" + nomProjet + '\'' +
                ", description='" + description + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                '}';
    }
}
