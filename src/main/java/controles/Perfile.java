package controles;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Perfile implements Initializable {
    public TextField FXnom;
    public TextField FXprenom;
    public TextField FXage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void AddPerson(ActionEvent actionEvent){
        String name= FXnom.getText();
        String prenom =FXprenom.getText();
        int age=Integer.parseInt(FXage.getText());
    }
}
