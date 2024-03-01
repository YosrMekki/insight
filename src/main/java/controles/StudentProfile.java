package controles;

import entities.Note;
import entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.StudentService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentProfile {
    @FXML
    private Button monCompteButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label firstNameDisplayLabel;
    @FXML
    private Label lastNameDisplayLabel;
    @FXML
    private Label birthDateDisplayLabel;
    @FXML
    private Label phoneNumberDisplayLabel;
    @FXML
    private Label cinDisplayLabel;
    @FXML
    private Label emailDisplayLabel;
    @FXML
    private Label passwordDisplayLabel;
    @FXML
    private Label firstNameText;
    @FXML
    private Label lastNameText;
    @FXML
    private Label birthDateText ;
    @FXML
    private Label phoneNumberText ;
    @FXML
    private Label cinText ;
    @FXML
    private Label emailText ;
    @FXML
    private Label passwordText;
    @FXML
    private Button modifierButton ;
    @FXML
    private BorderPane profilePane;
    @FXML
    private Button notesButton;
    @FXML
    private Button ajouterNoteButton;
    @FXML
    private TableView<Note> notesTableView;
    @FXML
    private TableColumn<Note,Void> actionsColumn ;
    private boolean isInfoVisible = false;

    StudentService studentService = new StudentService();
    private Student student;

    public void initialize() {
        // Set initial visibility of welcome label
        setStudent(student);
        welcomeLabel.setVisible(true);

        // Hide displayed labels initially
        hideDisplayedLabels();


        // Set action for the "Mon Compte" button
        monCompteButton.setOnAction(event -> {
            ajouterNoteButton.setVisible(false);
            notesTableView.setVisible(false);
            if (!isInfoVisible) {
                showDisplayedLabels();
                modifierButton.setVisible(true); // Show the "Modifier Informations" button

            } else {
                hideDisplayedLabels();
                modifierButton.setVisible(false); // hide the "Modifier Informations" button

            }
        });
        ajouterNoteButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Ajouter une note");
            dialog.setHeaderText(null);
            dialog.setContentText("Entrez le contenu de la note:");

            // Get the content of the note from the user
            dialog.showAndWait().ifPresent(noteContent -> {
                try {
                    studentService.addNote(student.getId(), noteContent);
                    displayNotes();

                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle SQL exception
                }
            });
        });

        // Set action for the "Notes" button
        notesButton.setOnAction(event -> {
            try {
                ajouterNoteButton.setVisible(true);
                initializeActionsColumn();
                List<Note> notes = studentService.getNotesByStudentId(student.getId());
                System.out.println(notes);
                // Display the retrieved notes in a dialog or another UI component
                displayNotes();
                // You can implement this based on your specific UI requirements
                for (Note note : notes) {
                    System.out.println(note.getNoteContent());
                }
                // Show the "Ajouter Note" button

            } catch (SQLException e) {
                e.printStackTrace();
                // Handle SQL exception
            }
        });

        // Set action for the "Ajouter Note" button

    }

    public void initData(Student student) {
        firstNameLabel.setText(student.getFirstName());
        lastNameLabel.setText(student.getLastName());
        firstNameDisplayLabel.setText(student.getFirstName());
        lastNameDisplayLabel.setText(student.getLastName());
        birthDateDisplayLabel.setText(student.getBirthDate().toString());
        phoneNumberDisplayLabel.setText(String.valueOf(student.getPhoneNumber()));
        cinDisplayLabel.setText(String.valueOf(student.getCin()));
        emailDisplayLabel.setText(student.getEmail());
    }

    private void showDisplayedLabels() {
        welcomeLabel.setVisible(true);
        firstNameLabel.setVisible(true);
        lastNameLabel.setVisible(true);
        firstNameDisplayLabel.setVisible(true);
        lastNameDisplayLabel.setVisible(true);
        birthDateDisplayLabel.setVisible(true);
        phoneNumberDisplayLabel.setVisible(true);
        cinDisplayLabel.setVisible(true);
        emailDisplayLabel.setVisible(true);
        firstNameText.setVisible(true);
        lastNameText.setVisible(true);
        birthDateText.setVisible(true);
        phoneNumberText.setVisible(true);
        cinText.setVisible(true);
        emailText.setVisible(true);
        passwordText.setVisible(false);
        profilePane.setVisible(true);
        modifierButton.setVisible(true);

        isInfoVisible = true;
    }

    private void hideDisplayedLabels() {
        //welcomeLabel.setVisible(false);
        //firstNameLabel.setVisible(false);
        //lastNameLabel.setVisible(false);
        firstNameDisplayLabel.setVisible(false);
        lastNameDisplayLabel.setVisible(false);
        birthDateDisplayLabel.setVisible(false);
        phoneNumberDisplayLabel.setVisible(false);
        cinDisplayLabel.setVisible(false);
        emailDisplayLabel.setVisible(false);
        firstNameText.setVisible(false);
        lastNameText.setVisible(false);
        birthDateText.setVisible(false);
        phoneNumberText.setVisible(false);
        cinText.setVisible(false);
        emailText.setVisible(false);
        passwordText.setVisible(false);
        profilePane.setVisible(false);
        modifierButton.setVisible(false);
        isInfoVisible = false;
    }


    public void handleModifierButtonAction(javafx.event.ActionEvent event) {
        try {
            // Load the popup FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/editStudentFromProfile.fxml"));
            Parent root = loader.load();

            // Get the controller
            EditStudentFromProfile controller = loader.getController();

            // Pass the student information to the popup controller
            try {
                controller.setStudent(studentService.getStudentByEmail(String.valueOf(emailDisplayLabel.getText()))); // Assuming 'student' is the current student object
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Create the popup stage
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Modifier Informations");
            popupStage.setScene(new Scene(root));

            // Show the popup
            popupStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayNotes() throws SQLException {
        notesTableView.setVisible(true);
        // Retrieve notes associated with the current student's ID from the database
        List<Note> notes = studentService.getNotesByStudentId(setStudent(student).getId());
        System.out.println(notes);
        // Display the retrieved notes
        for (Note note : notes) {
            System.out.println(note.getNoteContent()); // You can customize how you want to display the notes
        }
        ObservableList<Note> notesList = FXCollections.observableArrayList(notes);
        notesTableView.setItems(notesList);
    }
    public Student setStudent(Student student){
        this.student =student;
        return student ;
    }
    private void initializeActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox container = new HBox(editButton, deleteButton);

            {
                editButton.setStyle("-fx-background-color: #1554b3; -fx-text-fill: white; -fx-min-width: 40px; -fx-font-size: 12px;");
                deleteButton.setStyle("-fx-background-color: #1554b3; -fx-text-fill: white; -fx-min-width: 40px; -fx-font-size: 12px;");

                editButton.setOnAction(event -> {
                    // Handle edit action
                    handleEditButton(getIndex());
                });

                deleteButton.setOnAction(event -> {
                    // Handle delete action
                    handleDeleteButton(getIndex());
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

    private void handleEditButton(int index) {
        // Retrieve the note at the specified index
        Note note = notesTableView.getItems().get(index);

        // Open a dialog to edit the note content
        TextInputDialog dialog = new TextInputDialog(note.getNoteContent());
        dialog.setTitle("Edit Note");
        dialog.setHeaderText(null);
        dialog.setContentText("Edit Note Content:");

        // Get the result of the dialog
        Optional<String> result = dialog.showAndWait();

        // Update the note content if a new value is provided
        result.ifPresent(newNoteContent -> {
            // Update the note content in the database
            try {
                // Update the note content in the database
                studentService.updateNoteContent(note.getId(), newNoteContent);

                // Update the note object in the table view
                note.setNoteContent(newNoteContent);
                notesTableView.refresh();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while updating the note content.");
                alert.showAndWait();
            }
        });
    }


    private void handleDeleteButton(int index) {
        // Retrieve the note to be deleted
        Note note = notesTableView.getItems().get(index);

        // Create a confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this note?");

        // Show the confirmation dialog and wait for user input
        Optional<ButtonType> result = alert.showAndWait();

        // If the user confirms the deletion, delete the note
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Delete the note from the database
                studentService.deleteNoteById(note.getId());

                // Remove the note from the TableView
                notesTableView.getItems().remove(index);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("An error occurred while deleting the note.");
                errorAlert.showAndWait();
            }
        }
    }


    public void logout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/signin.fxml"));
            Scene scene = new Scene(root);

            // Get the stage information
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Set the new scene in the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
