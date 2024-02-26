package controles;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import models.Ecole;
import models.Professeur;
import services.EcoleService;
import services.ProfService;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherEcole implements Initializable {
    @FXML
    public Line line;
    @FXML
    public VBox professeursVBox;
    public GridPane ecolesGrid;


    private List<Ecole> ecoles;
    private List<Professeur> professeurs;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        EcoleService es = new EcoleService();
        try {
            ecoles=new ArrayList<>(es.recuperer());
            int column =0;
            int row = 1;
            for (Ecole ecole: ecoles){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Ecole.fxml"));
                Pane pane = fxmlLoader.load();
                EcoleController ec = fxmlLoader.getController();
                ec.setData(ecole);


                pane.setOnMouseClicked(event -> {
                    try {
                        FXMLLoader professeursLoader = new FXMLLoader(getClass().getResource("/Professeurs.fxml"));
                        Parent professeursParent = professeursLoader.load();
                        ProfesseurController pc = professeursLoader.getController();
                        ProfService profService=new ProfService();
                        // Ici, vous pouvez passer toute information nécessaire au contrôleur des professeurs
                        // en utilisant une méthode setter, si nécessaire
                        pc.setData(ecole.getId());
                        // puis récupérer ces informations dans le contrôleur des professeurs lors de son initialisation
                        // assurez-vous d'ajuster les contrôleurs en conséquence
                        Scene scene = new Scene(professeursParent);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        // Gérer l'erreur de chargement de "Professeurs.fxml"
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Erreur lors du chargement de la page des professeurs");
                        alert.showAndWait();
                    }
                });

                if(column == 2){
                    column = 0;
                    ++row;
                }
                ecolesGrid.add(pane,column++,row);
                GridPane.setMargin(pane,new Insets(20));
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
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



