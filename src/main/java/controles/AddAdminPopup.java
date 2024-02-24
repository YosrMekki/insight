package controles;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import entities.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import services.AdminService;

public class AddAdminPopup {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private DatePicker date;

    @FXML
    private TextField emailTextfield;

    @FXML
    private TextField lastNameTextfield;

    @FXML
    private TextField cinTextfield;

    @FXML
    private TextField phoneNumberTextfield;

    @FXML
    private PasswordField passwordTextfield;

    @FXML
    private TextField firstNameTextfield;
    AdminService adminService = new AdminService();
    public void initAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    @FXML
    void addAdmin(ActionEvent event) {
        String email = emailTextfield.getText();
        String password = passwordTextfield.getText();
        String firstName = firstNameTextfield.getText();
        String lastName = lastNameTextfield.getText();
        LocalDate birthDate = date.getValue();
        int phoneNumber = Integer.parseInt(phoneNumberTextfield.getText());
        int cin = Integer.parseInt(cinTextfield.getText());

        Admin admin = new Admin(email, password, firstName, lastName, java.sql.Date.valueOf(birthDate), phoneNumber, cin);

        try {
            adminService.add(admin);
            // Optionally, you can close the popup here
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
        }

    }

    @FXML
    void initialize() {


    }

}
