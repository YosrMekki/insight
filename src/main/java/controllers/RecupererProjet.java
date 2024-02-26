package controllers;

import entities.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import services.ProjetService;

import java.sql.SQLException;
import java.util.List;

public class RecupererProjet {

    @FXML
    private TableView<Projet> tableView;

    @FXML
    private TableColumn<Projet, Integer> idColumn;

    @FXML
    private TableColumn<Projet, String> nomProjetColumn;

    @FXML
    private TableColumn<Projet, String> descriptionColumn;

    @FXML
    private TableColumn<Projet, String> nomEntrepriseColumn;

    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProjetProperty().asObject());
        nomProjetColumn.setCellValueFactory(cellData -> cellData.getValue().nomProjetProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        nomEntrepriseColumn.setCellValueFactory(cellData -> cellData.getValue().nomEntrepriseProperty());

        ProjetService projetService = new ProjetService();
        try {
            List<Projet> projets = projetService.recuperer();
            ObservableList<Projet> projetList = FXCollections.observableArrayList(projets);
            tableView.setItems(projetList);
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'exception selon votre besoin
        }
    }
}
