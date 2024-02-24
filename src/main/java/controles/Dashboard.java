package controles;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import entities.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import services.AdminService;
import services.StudentService;

public class Dashboard {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> lastNameColumn;


    @FXML
    private TableColumn<Student, Integer> cinColumn;

    @FXML
    private TableColumn<Student, Integer> phoneNumberColumn;

    @FXML
    private TableColumn<Student, Date> birthDateColumn;
    @FXML
    private TableColumn<Student, String> emailColumn;


    @FXML
    private TableColumn<Student, Void> actionsColumn;
    @FXML
    private Button studentsButton;

    private final StudentService studentService = new StudentService();
    AdminService   adminService = new AdminService();




    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Initialize actions column
        initializeActionsColumn();

        try {
            displayStudents();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }


    public void initData(String firstName, String lastName) {
        firstNameLabel.setText("First Name: " + firstName);
        lastNameLabel.setText("Last Name: " + lastName);
    }

    @FXML
    private void showStudents() {
        try {
            List<Student> studentList = studentService.Display();
            ObservableList<Student> students = FXCollections.observableArrayList(studentList);
            studentTable.setItems(students);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    private void displayStudents() throws SQLException {
        ObservableList<Student> students = FXCollections.observableArrayList(studentService.Display());
        studentTable.setItems(students);
    }

    private void initializeActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {
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




    @FXML
    private void handleEditButton() {
        // Get the selected student from the table
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Create an instance of the EditStudentPopup dialog
            EditStudentPopup editStudentPopup = new EditStudentPopup(selectedStudent);

            // Show the dialog and wait for the user response
            Optional<Pair<Student, String>> result = editStudentPopup.showAndWait();

            // If the user clicked the Save button, update the student information
            result.ifPresent(pair -> {
                Student updatedStudent = pair.getKey();
                String newEmail = pair.getValue();

                // Update the selected student with the new information
                selectedStudent.setEmail(newEmail);
                selectedStudent.setFirstName(updatedStudent.getFirstName());
                selectedStudent.setLastName(updatedStudent.getLastName());
                selectedStudent.setBirthDate(updatedStudent.getBirthDate());
                selectedStudent.setCin(updatedStudent.getCin());
                selectedStudent.setPhoneNumber(updatedStudent.getPhoneNumber());

                try {
                    // Update the changes in the database using the StudentService
                    studentService.update(selectedStudent);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }

                // Refresh the TableView to reflect the changes
                studentTable.refresh();
            });
        } else {
            // No student selected, display an error message or handle accordingly
            System.out.println("No student selected for editing.");
        }
    }


    @FXML
    private void handleDeleteButton() {
        // Get the selected student from the table
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Student");
            alert.setContentText("Are you sure you want to delete " + selectedStudent.getFirstName() + " " + selectedStudent.getLastName() + "?");

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
                    studentService.delete(selectedStudent);

                    // Remove the selected student from the TableView
                    studentTable.getItems().remove(selectedStudent);
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


    public void showDataTable(javafx.scene.input.MouseEvent mouseEvent) {
        studentTable.setVisible(true);
    }


    public void addAdmin(ActionEvent actionEvent) {


        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addAdminPopup.fxml"));
            Parent root = fxmlLoader.load();
            AddAdminPopup controller = fxmlLoader.getController();
            controller.initAdminService(adminService); // Pass the AdminService to the controller
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}
