package controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Ecole;
import services.EcoleService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AjouterEcole implements Initializable {
    @FXML
    public TextField nomFX;

    @FXML
    public TextField nb_professeurFX;
    public TextField addresseFX;
    public Button valider;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



    public void AddEcole(javafx.event.ActionEvent actionEvent) {
        EcoleService ecoleService=new EcoleService();

        String nom= nomFX.getText();
        String adresse =addresseFX.getText();
        int nb_professeur=Integer.parseInt(nb_professeurFX.getText());


        if (nb_professeur > 10) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText(null);
            alert.setContentText("Le nombre de professeurs ne peut pas dépasser 10.");
            alert.showAndWait();
            return;
        }
        try {
            if (ecoleService.isNomEcoleExist(nom)) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText(null);
                alert.setContentText("L'ecole existe deja");
                alert.showAndWait();
                return;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        Ecole ecole=new Ecole(nom,adresse,nb_professeur);
        try {
            ecoleService.ajouter(ecole);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addEcole(ActionEvent actionEvent) {
        try {
            System.out.println("hi");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEcole.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            System.out.println("hi");

            stage.show();
        } catch (Exception e) {
            System.out.println("hellooooo");

            e.printStackTrace();
        }
    }
}
