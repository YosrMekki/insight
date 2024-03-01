package controles;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Professor;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import services.AdminService;
import services.ProfessorService;
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
    private Label professorfirstNameLabel;

    @FXML
    private Label professorlastNameLabel;

    @FXML
    private TableView<Professor> professorTable;

    @FXML
    private TableColumn<Professor, Integer> idprofessorColumn;

    @FXML
    private TableColumn<Professor, String> professorfirstNameColumn;

    @FXML
    private TableColumn<Professor, String> professorlastNameColumn;


    @FXML
    private TableColumn<Professor, Integer> professorcinColumn;

    @FXML
    private TableColumn<Professor, Integer> professorphoneNumberColumn;

    @FXML
    private TableColumn<Professor, Date> professorbirthDateColumn;
    @FXML
    private TableColumn<Professor, String> professoremailColumn;


    @FXML
    private TableColumn<Professor, Void> professoractionsColumn;
    @FXML
    private Button studentsButton;
    @FXML
    private Button professorsButton;
    @FXML
    private Button logoutButton;
    @FXML
    private TextField searchField ;
    @FXML
    private Button searchid ;


    private final StudentService studentService = new StudentService();
    AdminService   adminService = new AdminService();
    ProfessorService professorService = new ProfessorService() ;





    @FXML
    void initialize() {
        try {
            // Initialize cell value factories for students
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            cinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
            birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            // Initialize cell value factories for professors
            idprofessorColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            professorfirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            professorlastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            professorphoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
            professorcinColumn.setCellValueFactory(new PropertyValueFactory<>("cin"));
            professorbirthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
            professoremailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

            // Initialize actions column for students
            initializeActionsColumn();

            // Initialize actions column for professors
            initializeProfessorActionsColumn();

            // Display students
            displayStudents();

            // Display professors
            displayProfessors();
        } catch (SQLException e) {
            e.printStackTrace();
            // Log or handle the exception
        }
    }




    public void initData(String firstName, String lastName) {
        firstNameLabel.setText(  firstName+"");
        lastNameLabel.setText(lastName);
        System.out.println(firstName+ lastName);
    }




    private void displayStudents() throws SQLException {
        ObservableList<Student> students = FXCollections.observableArrayList(studentService.Display());
        studentTable.setItems(students);
    }
    private void displayProfessors() throws SQLException {
        ObservableList<Professor> professors = FXCollections.observableArrayList(professorService.Display());
        professorTable.setItems(professors);
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

    private void initializeProfessorActionsColumn() {
        professoractionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");
            private final HBox container = new HBox(editButton, deleteButton);

            {
                editButton.setStyle("-fx-background-color: #1554b3; -fx-text-fill: white; -fx-min-width: 40px; -fx-font-size: 12px;");
                deleteButton.setStyle("-fx-background-color: #1554b3; -fx-text-fill: white; -fx-min-width: 40px; -fx-font-size: 12px;");

                editButton.setOnAction(event -> {
                    // Handle edit action
                    handleProfessorEditButton();
                });

                deleteButton.setOnAction(event -> {
                    // Handle delete action
                    handleProfessorDeleteButton();
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
    private void handleProfessorEditButton() {
        // Get the selected student from the table
        Professor selectedProfessor = professorTable.getSelectionModel().getSelectedItem();

        if (selectedProfessor != null) {
            // Create an instance of the EditStudentPopup dialog
            EditProfessorPopup editStudentPopup = new EditProfessorPopup(selectedProfessor);

            // Show the dialog and wait for the user response
            Optional<Pair<Professor, String>> result = editStudentPopup.showAndWait();

            // If the user clicked the Save button, update the student information
            result.ifPresent(pair -> {
                Professor updatedProfessor = pair.getKey();
                String newEmail = pair.getValue();

                // Update the selected student with the new information
                selectedProfessor.setEmail(newEmail);
                selectedProfessor.setFirstName(selectedProfessor.getFirstName());
                selectedProfessor.setLastName(selectedProfessor.getLastName());
                selectedProfessor.setBirthDate(selectedProfessor.getBirthDate());
                selectedProfessor.setCin(selectedProfessor.getCin());
                selectedProfessor.setPhoneNumber(selectedProfessor.getPhoneNumber());

                try {
                    // Update the changes in the database using the StudentService
                    professorService.update(selectedProfessor);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }

                // Refresh the TableView to reflect the changes
                studentTable.refresh();
            });
        } else {
            // No student selected, display an error message or handle accordingly
            System.out.println("No professor selected for editing.");
        }
    }
    @FXML
    private void handleProfessorDeleteButton() {
        // Get the selected professor from the table
        Professor selectedProfessor = professorTable.getSelectionModel().getSelectedItem();

        if (selectedProfessor != null) {
            // Create a confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Delete Student");
            alert.setContentText("Are you sure you want to delete " + selectedProfessor.getFirstName() + " " + selectedProfessor.getLastName() + "?");

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
                    professorService.delete(selectedProfessor);

                    // Remove the selected student from the TableView
                    professorTable.getItems().remove(selectedProfessor);
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Handle the exception
                }
            }
        } else {
            // No student selected, display an error message or handle accordingly
            System.out.println("No professor selected for deletion.");
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

    @FXML
    public void showDataTable() {
        professorTable.setVisible(false);
        studentTable.setVisible(true);
        searchField.setVisible(true);
        searchid.setVisible(true);
    }
    public void showProfessorDataTable(ActionEvent actionEvent) {
        studentTable.setVisible(false);
        professorTable.setVisible(true);}

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

    public void addProfessor(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/addProfessorPopup.fxml"));
            Parent root = fxmlLoader.load();
            AddProfessorPopup controller = fxmlLoader.getController();
            controller.initProfessorService(professorService); // Pass the Prof Service to the controller
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));

            // Show the popup and wait for it to be closed
            stage.showAndWait();

            // Reload the professor table data after the popup is closed
            professorTable.getItems().clear(); // Clear existing data
            try {
                professorTable.getItems().addAll(professorService.Display()); // Reload data from service
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }


    @FXML
    void logout(ActionEvent event) {
        try {
            // Load the sign-in interface (assuming it's named "SignIn.fxml")
            Parent root = FXMLLoader.load(getClass().getResource("/Signin.fxml"));
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


    @FXML
    private void handleSearch(ActionEvent event) {
        String searchTerm = searchField.getText().trim();
        if (!searchTerm.isEmpty()) {
            // Filter the student and professor lists based on the search term
            filterStudents(searchTerm);
            filterProfessors(searchTerm);
        } else {
            // If the search term is empty, display all students and professors
            try {
                displayStudents();
                displayProfessors();
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }
    }

    private void filterStudents(String searchTerm) {
        try {
            List<Student> filteredStudents = studentService.searchStudents(searchTerm);
            studentTable.setItems(FXCollections.observableArrayList(filteredStudents));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    private void filterProfessors(String searchTerm) {
        try {
            List<Professor> filteredProfessors = professorService.searchProfessors(searchTerm);
            professorTable.setItems(FXCollections.observableArrayList(filteredProfessors));
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }
}

