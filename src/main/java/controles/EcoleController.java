package controles;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.Ecole;

import java.awt.*;

public class EcoleController {


    public Label nomFX;
    public Label adresseFX;
    public Label nb_professeurs;

    public void setData(Ecole ecole){
        nomFX.setText(ecole.getNomEcole());
        adresseFX.setText(ecole.getAdresse());
        nb_professeurs.setText(String.valueOf(ecole.getNb_professeur()));
    }
}
