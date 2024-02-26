package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProjetInfo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea DescriptionProjet;

    @FXML
    private TextField nomEntreprise;

    @FXML
    private TextField nonProjet;

    public void setDescriptionProjet(String descriptionProjet) {
        this.DescriptionProjet.setText(descriptionProjet);
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise.setText(nomEntreprise);
    }

    public void setNonProjet(String nonProjet) {
        this.nonProjet.setText(nonProjet);
    }

    @FXML
    void initialize() {}

}

