package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TestInfo {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField dureetextfield;

    @FXML
    private TextField matieretextfield;

    @FXML
    private TextField notetextfield;

    public void setDureetextfield(String dureetextfield) {
        this.dureetextfield.setText(dureetextfield);
    }

    public void setMatieretextfield(String matieretextfield) {
        this.matieretextfield.setText(matieretextfield);
    }

    public void setNotetextfield(String notetextfield) {
        this.notetextfield.setText(notetextfield);
    }

    @FXML
    void initialize() {

    }

}
