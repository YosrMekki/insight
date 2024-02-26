package controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.EcoleService;
import services.ProfService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AjouterProfesseur implements Initializable {
    public TextField nomFX;
    public TextField prenomFX;
    public TextField adresseFX;
    public Button valider;
    public TextField telephoneFX;

    @FXML
    public ChoiceBox ecole;
    EcoleService ecoleService=new EcoleService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<String> nomsEcoles = ecoleService.getNomsEcoles();
        ecole.getItems().addAll(nomsEcoles);

    }

    public void AddProfesseur(ActionEvent actionEvent) {
        ProfService ps=new ProfService();
        String nom=nomFX.getText();
        String prenom=prenomFX.getText();
        String adresse =adresseFX.getText();
        int num_tel=Integer.parseInt(telephoneFX.getText());
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