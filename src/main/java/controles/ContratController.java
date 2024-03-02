package controles;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Contrat;
import models.Professeur;
import services.ContratService;
import services.ProfService;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ContratController implements Initializable {

    public TableColumn<Contrat,Integer> idColumn;
    public TableView<Contrat>  contratTable;
    public TableColumn<Contrat,Integer>  ecoleColumn;
    public TableColumn<Contrat,Integer>  dureeColumn;
    public TableColumn<Contrat, Date>  date_contrat;
    public Button addContartButton;
    public TableColumn<Contrat,Void>  contratactionsColumn;

    private ContratService contratService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contratService = new ContratService();



        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ecoleColumn.setCellValueFactory(new PropertyValueFactory<>("ecole_id"));

        date_contrat.setCellValueFactory(new PropertyValueFactory<>("date_contrat"));
        dureeColumn.setCellValueFactory(new PropertyValueFactory<>("nb_days"));


        initializeActionsColumn();

        try {
            System.out.println("hello");
            displayContart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void displayContart() throws SQLException {
        List<Contrat> contratsList = contratService.recuperer();
        System.out.println(contratsList);

        ObservableList<Contrat> contrats = FXCollections.observableArrayList(contratsList);

        contratTable.setItems(contrats);
    }

    private void initializeActionsColumn() {
        contratactionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final Button uploadButton = new Button("Print");

            private final HBox container = new HBox(editButton, deleteButton,uploadButton);

            {
                editButton.setOnAction(event -> {
                    // Handle edit action
                    handleEditButton();
                });

                deleteButton.setOnAction(event -> {
                    // Handle delete action
                    handleDeleteButton();
                });

                    uploadButton.setOnAction(event -> {
                        // Handle edit action
                        handlePrintButton();
                    });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(container);
                }
            }
        });
    }

    private void handlePrintButton() {
        Contrat contrat= contratTable.getSelectionModel().getSelectedItem();
        contratService.printContrat(contrat);
    }


    private void handleEditButton() {
        Contrat selectedContrat = contratTable.getSelectionModel().getSelectedItem();

        if (selectedContrat != null) {
            // Create an instance of the EditStudentPopup dialog
            ContratPoopapController editcontratPopup = new ContratPoopapController(selectedContrat);

            // Show the dialog and wait for the user response
            Optional<Pair<Contrat, Integer>> result = editcontratPopup.showAndWait();

            // If the user clicked the Save button, update the student information
            result.ifPresent(pair -> {
                Contrat updatedContrat = pair.getKey();
                Integer newEmail = pair.getValue();

                // Update the selected student with the new information
                selectedContrat.setNb_days(updatedContrat.getNb_days());

                try {
                    // Update the changes in the database using the StudentService
                    System.out.println("waaa");
                    contratService.modifier(selectedContrat);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }


                // Refresh the TableView to reflect the changes
                contratTable.refresh();
            });
        } else {
            // No student selected, display an error message or handle accordingly
            System.out.println("No Contrat selected for editing.");
        }
    }



    @FXML
    public void handleProfesseurClick(javafx.scene.input.MouseEvent mouseEvent) {

    }




    private void handleDeleteButton() {
        Contrat selectedContrat = contratTable.getSelectionModel().getSelectedItem();

        if (selectedContrat != null) {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Contrat");
            alert.setContentText("Are you sure you want to delete " + selectedContrat.getNb_days() + " " + selectedContrat.getNb_days() + "?");

            // Add OK and Cancel buttons to the dialog
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            // Show the confirmation dialog and wait for user response
            Optional<ButtonType> result = alert.showAndWait();

            // If the user confirms deletion, proceed with deletion
            if (result.isPresent() && result.get() == okButton) {
                try {
                    // Delete the selected student from the database using the StudentService
                    contratService.supprimer(selectedContrat.getId());

                    // Remove the selected student from the TableView
                    contratTable.getItems().remove(selectedContrat);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }
            }
        } else {
            // No student selected, display an error message or handle accordingly
            System.out.println("No student selected for deletion.");
        }
    }
    public void addEcole(ActionEvent actionEvent) {
        try {

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


}
