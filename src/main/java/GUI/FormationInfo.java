package GUI;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.Formation;
import services.FormationService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FormationInfo implements Initializable {


    @FXML
    private TextField domainetextfield;

    @FXML
    private TextField idtextfield;

    @FXML
    private TextField montanttextfield;

    @FXML
    private TextField nomtextfield;

    public void setDomainetextfield(String domainetextfield) {
        this.domainetextfield.setText(domainetextfield);
    }

    public void setIdtextfield(String idtextfield) {
        this.idtextfield.setText(idtextfield);
    }

    public void setMontanttextfield(String montanttextfield) {
        this.montanttextfield .setText(montanttextfield);
    }

    public void setNomtextfield(String nomtextfield) {
        this.nomtextfield .setText(nomtextfield);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FormationService fs = new FormationService();
        try {
            fs.recuperer();





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }




}
