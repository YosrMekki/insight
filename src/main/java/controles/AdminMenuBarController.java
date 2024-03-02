package controles;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;


import java.io.IOException;

public class AdminMenuBarController {

    public Button addEcoleButton;
    public Button ajouterButton;
    public Button consulterButton;
    public VBox ajouterConsulterBox;
    public VBox otherButtonsBox;
    private boolean buttonsVisible = false;


    private void initialize() {
        ajouterConsulterBox.setMargin(otherButtonsBox, new Insets(10, 0, 0, 0)); // Définir la marge entre les VBox
    }

    public void Ecole(ActionEvent actionEvent) {
        if (!buttonsVisible) {
            ajouterConsulterBox.setVisible(true);
            ajouterConsulterBox.setManaged(true);
            buttonsVisible = true;
            otherButtonsBox.setMargin(otherButtonsBox, new Insets(40, 0, 0, 40));


        } else {
            ajouterConsulterBox.setVisible(false);
            ajouterConsulterBox.setManaged(false);
            buttonsVisible = false;
            otherButtonsBox.setMargin(otherButtonsBox, new Insets(0, 0, 0, 0));

        }
        }





        public void addEcole(ActionEvent actionEvent) {
        try {
            System.out.println("hi");

            // Charger la vue AjouterEcole.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEcole.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            System.out.println("hi");

            // Afficher la fenêtre
            stage.show();
        } catch (Exception e) {
            System.out.println("hellooooo");

            e.printStackTrace();
        }
    }

    public void addProf(ActionEvent actionEvent) {

        try {
            System.out.println("hi");

            // Charger la vue AjouterEcole.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProfesseur.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            System.out.println("hi");

            // Afficher la fenêtre
            stage.show();
        } catch (Exception e) {
            System.out.println("hellooooo");

            e.printStackTrace();
        }
        }
    }
