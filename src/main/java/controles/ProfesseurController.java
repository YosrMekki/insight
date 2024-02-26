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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import models.Professeur;
import services.ProfService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProfesseurController implements Initializable {
    public Label nomLabel;
    public Label prenomLabel;
    public Button addProfessorButton;
    public TableView<Professeur> professorTable;
    public Button addEcole;
    @FXML
    private TableColumn<Professeur, Void> professoractionsColumn;



    @FXML
    private TableColumn<Professeur, String> adresseColumn;

    @FXML
    private TableColumn<Professeur, Integer> idColumn;

    @FXML
    private TableColumn<Professeur, String> nomColumn;



    @FXML
    private TableColumn<Professeur, Integer> phoneNumberColumn;

    @FXML
    private TableColumn<Professeur, String> prenomColumn;






    @FXML
    private Label welcomeLabel;
    private ProfService profService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        profService = new ProfService();

    }

    public void setData(int id) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("num_tel"));

        initializeActionsColumn();

        try {
            displayProfessors(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private void initializeActionsColumn() {
        professoractionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox container = new HBox(editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    // Handle edit action
                    handleEditButton();
                });

                deleteButton.setOnAction(event -> {
                    // Handle delete action
                    handleDeleteButton();
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


    private void handleEditButton() {
        Professeur selectedProfesseur = professorTable.getSelectionModel().getSelectedItem();

        if (selectedProfesseur != null) {
            // Create an instance of the EditStudentPopup dialog
            EditProfesseurPopup editStudentPopup = new EditProfesseurPopup(selectedProfesseur);

            // Show the dialog and wait for the user response
            Optional<Pair<Professeur, String>> result = editStudentPopup.showAndWait();

            // If the user clicked the Save button, update the student information
            result.ifPresent(pair -> {
                Professeur updatedProfesseur = pair.getKey();
                String newEmail = pair.getValue();

                // Update the selected student with the new information
                selectedProfesseur.setNom(updatedProfesseur.getNom());
                selectedProfesseur.setPrenom(updatedProfesseur.getPrenom());
                selectedProfesseur.setAdresse(updatedProfesseur.getAdresse());
                selectedProfesseur.setNum_tel(updatedProfesseur.getNum_tel());

                try {
                    // Update the changes in the database using the StudentService
                    profService.modifier(selectedProfesseur);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }

                // Refresh the TableView to reflect the changes
                professorTable.refresh();
            });
        } else {
            // No student selected, display an error message or handle accordingly
            System.out.println("No student selected for editing.");
        }
    }


    public void displayProfessors(int id) throws SQLException {
        List<Professeur> professeursList = profService.recupererProfParEcoleId(id);
        ObservableList<Professeur> professeurs = FXCollections.observableArrayList(professeursList);
        System.out.println(professeurs);

        professorTable.setItems(professeurs);
    }
    @FXML
    public void handleProfesseurClick(javafx.scene.input.MouseEvent mouseEvent) {

    }

    public void addEcole(ActionEvent actionEvent) {
        try {
            System.out.println("hi");

            // Charger la vue AjouterEcole.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEcole.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(scene);
            System.out.println("hi");

            // Afficher la fenêtre
            stage.show();
        } catch (Exception e) {
            System.out.println("hellooooo");

            e.printStackTrace();
            // Gérer les exceptions, par exemple en affichant une boîte de dialogue d'erreur
        }
    }


    private void handleDeleteButton() {
            Professeur selectedProfesseur = professorTable.getSelectionModel().getSelectedItem();

        if (selectedProfesseur != null) {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Student");
            alert.setContentText("Are you sure you want to delete " + selectedProfesseur.getNom() + " " + selectedProfesseur.getPrenom() + "?");

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
                    profService.supprimer(selectedProfesseur.getId());

                    // Remove the selected student from the TableView
                    professorTable.getItems().remove(selectedProfesseur);
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
    }
