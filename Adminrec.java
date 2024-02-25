package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import models.reclamation;
import services.reclamationService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Adminrec {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button deleteButton;

    @FXML
    private Button displayButton;

    @FXML
    private TableColumn<reclamation, String> emailrec;

    @FXML
    private TableColumn<reclamation, String> fullname;

    @FXML
    private TableColumn<reclamation, Integer> idrec;

    @FXML
    private TableColumn<reclamation, String> message;

    @FXML
    private TableColumn<reclamation, Integer> pnrec;

    @FXML
    private TableColumn<reclamation, Integer> stater;

    @FXML
    private TableColumn<reclamation, String> subject;

    @FXML
    private TableView<reclamation> tableview;
    private final reclamationService recs = new reclamationService();

    @FXML
    void display(ActionEvent event) {
        try {
            List<reclamation> reclamationList = recs.getReclamationList();
            tableview.setItems(FXCollections.observableArrayList(reclamationList));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierec(ActionEvent event) {
        reclamation selectedReclamation = tableview.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit Stater");
            dialog.setHeaderText("Editing Stater for Reclamation ID: " + selectedReclamation.getIdrec());
            dialog.setContentText("Please enter the new value for Stater:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(newStater -> {
                try {
                    int newStaterInt = Integer.parseInt(newStater);
                    selectedReclamation.setStater(newStaterInt);
                    recs.modifiereclamtion(newStaterInt, selectedReclamation.getIdrec());
                    tableview.refresh();
                } catch (NumberFormatException | SQLException e) {
                    e.printStackTrace();
                }
            });
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
            noSelectionAlert.setTitle("No Selection");
            noSelectionAlert.setHeaderText(null);
            noSelectionAlert.setContentText("Please select a reclamation to edit.");
            noSelectionAlert.showAndWait();
        }
    }

    @FXML
    void supprimerec(ActionEvent event) {
        reclamation selectedReclamation = tableview.getSelectionModel().getSelectedItem();

        if (selectedReclamation != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Reclamation");
            alert.setContentText("Are you sure you want to delete this reclamation?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == okButton) {
                try {
                    recs.supprimereclamtion(selectedReclamation.getIdrec());
                    tableview.getItems().remove(selectedReclamation);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
            noSelectionAlert.setTitle("No Selection");
            noSelectionAlert.setHeaderText(null);
            noSelectionAlert.setContentText("Please select a reclamation to delete.");
            noSelectionAlert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        emailrec.setCellValueFactory(new PropertyValueFactory<>("emailrec"));
        fullname.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        idrec.setCellValueFactory(new PropertyValueFactory<>("idrec"));
        message.setCellValueFactory(new PropertyValueFactory<>("message"));
        pnrec.setCellValueFactory(new PropertyValueFactory<>("pnrec"));
        stater.setCellValueFactory(new PropertyValueFactory<>("stater"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
    }
}
