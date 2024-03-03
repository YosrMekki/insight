package controllers;

import entities.Projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import services.ProjetService;
import javax.mail.Authenticator;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import javafx.scene.control.Alert;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import javafx.collections.transformation.SortedList;

public class ProjetController implements Initializable {

    @FXML
    private TableView<Projet> projetTable;

    @FXML
    private TableColumn<Projet, Integer> idProjetTV;

    @FXML
    private TableColumn<Projet, String> nomProjetTV;

    @FXML
    private TableColumn<Projet, String> descriptionTV;

    @FXML
    private TableColumn<Projet, String> nomEntrepriseTV;

    @FXML
    private TableColumn<Projet, String> domaineTV;

    @FXML
    private TableColumn<Projet, String> mailProfesseurTV;

    @FXML
    private TextArea descriptionProjet;

    @FXML
    private TextField nomEntreprise;

    @FXML
    private TextField nomProjet;

    @FXML
    private ComboBox<String> comboboxDomaine;

    @FXML
    private TextField mailProfesseur;

    @FXML
    private TextField keyWordsTF; // Ajout du TextField pour la recherche
    @FXML
    private BarChart<String, Number> projetChart;
    @FXML
    private CategoryAxis xAxis; // L'axe des catégories (Domaines)
    @FXML
    private NumberAxis yAxis; // L'axe des nombres (Nombre de projets)
    private final ProjetService projetService = new ProjetService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idProjetTV.setCellValueFactory(new PropertyValueFactory<>("idProjet"));
        nomProjetTV.setCellValueFactory(new PropertyValueFactory<>("nomProjet"));
        descriptionTV.setCellValueFactory(new PropertyValueFactory<>("description"));
        nomEntrepriseTV.setCellValueFactory(new PropertyValueFactory<>("nomEntreprise"));
        comboboxDomaine.setItems(FXCollections.observableArrayList("Développement web", "Cyber securité", "Intelligence artificielle"));
        domaineTV.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        mailProfesseurTV.setCellValueFactory(new PropertyValueFactory<>("email"));

        // Initialise la colonne des actions
        initializeActionsColumn();

        try {
            displayProjets();
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }

        projetTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayProjetDetails(newValue);
            }
        }

        );

        // Ajout de l'écouteur pour la recherche
        keyWordsTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterProjects(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'exception
            }

        });
        try {
            displayProjets();
            updateProjetChart(); // Mettez à jour le graphique après avoir affiché les projets
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'exception
        }
    }

    @FXML
    void ajouterProjet(ActionEvent event) {
        // Vérifier si tous les champs sont remplis

        if (nomProjet.getText().isEmpty() || descriptionProjet.getText().isEmpty() || nomEntreprise.getText().isEmpty() || comboboxDomaine.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez remplir tous les champs du formulaire.");
            alert.show();
            return;
        }
        // Vérifier si l'e-mail du professeur est valide
        if (!isValidEmail(mailProfesseur.getText())) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez saisir une adresse e-mail valide pour le professeur.");
            alert.show();
            return;
        }


        Projet projet = new Projet(
                nomProjet.getText(),
                descriptionProjet.getText(),
                nomEntreprise.getText(),
                comboboxDomaine.getValue(), // Utiliser la valeur sélectionnée dans le ComboBox
                mailProfesseur.getText()
        );

        try {
            projetService.ajouter(projet);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Le projet a été ajouté avec succès");
            alert.show();

            updateProjetChart();
            displayProjets();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }

    }
    // Méthode pour valider un e-mail
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }



    private void initializeActionsColumn() {
        TableColumn<Projet, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setPrefWidth(100);
        actionsColumn.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");
            private final HBox container = new HBox(editButton, deleteButton);

            {
                editButton.setOnAction(event -> {
                    Projet projet = getTableView().getItems().get(getIndex());
                    openEditProjetPopup(projet);
                });

                deleteButton.setOnAction(event -> {
                    Projet projet = getTableView().getItems().get(getIndex());
                    supprimerProjet(projet);
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
        projetTable.getColumns().add(actionsColumn);
    }

    private void openEditProjetPopup(Projet projet) {
        EditProjetPopup editProjetPopup = new EditProjetPopup(projet);
        Optional<Pair<Projet, String>> result = editProjetPopup.showAndWait();
        result.ifPresent(pair -> {
            Projet updatedProjet = pair.getKey();
            String nomProjet = pair.getValue();
            try {
                projetService.modifier(updatedProjet);
                displayProjets();
            } catch (SQLException e) {
                e.printStackTrace();
                // Gérer l'exception
            }
        });
    }

    @FXML
    void supprimerProjet(Projet event) {
        Projet projet = projetTable.getSelectionModel().getSelectedItem();
        if (projet != null) {
            try {
                projetService.supprimer(projet.getIdProjet());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le projet a été supprimé avec succès");
                alert.show();

                displayProjets();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(e.getMessage());
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez sélectionner un projet à supprimer.");
            alert.show();
        }
    }

    private void displayProjets() throws SQLException {
        List<Projet> projetsList = projetService.recuperer();
        ObservableList<Projet> projets = FXCollections.observableArrayList(projetsList);
        projetTable.setItems(projets);

        // Tri par domaine
        projetTable.getSortOrder().clear(); // Efface les tris précédents
        domaineTV.setSortType(TableColumn.SortType.ASCENDING); // Tri par ordre croissant
        projetTable.getSortOrder().add(domaineTV); // Applique le tri
        projetTable.sort();
    }

    private void displayProjetDetails(Projet projet) {
        nomProjet.setText(projet.getNomProjet());
        descriptionProjet.setText(projet.getDescription());
        nomEntreprise.setText(projet.getNomEntreprise());
        comboboxDomaine.setValue(projet.getDomaine());
        mailProfesseur.setText(projet.getEmail());
    }

    // Méthode pour filtrer les projets en fonction des mots-clés de recherche
    private void filterProjects(String keyword) throws SQLException {
        List<Projet> projetsList = projetService.recuperer();
        ObservableList<Projet> projets = FXCollections.observableArrayList(projetsList);
        FilteredList<Projet> filteredData = new FilteredList<>(projets, p -> true);

        if (keyword != null && !keyword.isEmpty()) {
            filteredData.setPredicate(projet -> {
                String lowerCaseKeyword = keyword.toLowerCase();
                return projet.getNomProjet().toLowerCase().contains(lowerCaseKeyword)
                        || projet.getDescription().toLowerCase().contains(lowerCaseKeyword)
                        || projet.getNomEntreprise().toLowerCase().contains(lowerCaseKeyword)
                        || projet.getDomaine().toLowerCase().contains(lowerCaseKeyword)
                        || projet.getEmail().toLowerCase().contains(lowerCaseKeyword);
            });
        }

        projetTable.setItems(filteredData);
    }
    private void updateProjetChart() throws SQLException {
        ObservableList<Projet> projets = FXCollections.observableArrayList(projetService.recuperer());
        Map<String, Integer> projetCountByDomaine = new HashMap<>();

        // Comptage des projets par domaine
        for (Projet projet : projets) {
            String domaine = projet.getDomaine();
            if (!projetCountByDomaine.containsKey(domaine)) {
                projetCountByDomaine.put(domaine, 1);
            } else {
                projetCountByDomaine.put(domaine, projetCountByDomaine.get(domaine) + 1);
            }
        }
        // Préparation des données pour le BarChart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ObservableList<String> domaines = comboboxDomaine.getItems();
        for (String domaine : domaines) {
            int count = projetCountByDomaine.getOrDefault(domaine, 0);
            series.getData().add(new XYChart.Data<>(domaine, count));
        }

        projetChart.getData().clear();
        projetChart.getData().add(series);
    }



    @FXML
    void envoyerMailProfesseur(ActionEvent event) {
        // Récupérer les informations du projet depuis les champs texte
        String domaine = domaineTV.getText();
        String description = descriptionProjet.getText();
        String nomEntreprise = nomEntrepriseTV.getText();
        String nomProjet = nomProjetTV.getText();
        // Récupérer l'adresse e-mail du professeur depuis le champ texte
        String emailProfesseur = mailProfesseur.getText();

        // Vérifier si l'adresse e-mail du professeur est vide
        if (emailProfesseur.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Veuillez saisir l'adresse e-mail du professeur.");
            alert.show();
            return;
        }

        // Configurer les informations de connexion au serveur SMTP
        String host = "smtp.gmail.com";
        String port = "587"; // Port SMTP (TLS)
        String username = "pinsight76@gmail.com";
        String password = "hfee wchg dbta qazd";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session de messagerie
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer le message e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("pinsight76@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailProfesseur));
            message.setSubject("Affectation projet");

            // Contenu du message avec les détails du projet
            String contenuMessage = "Bonjour Professeur,\n\n"
                    + "Nous avons le plaisir de vous informer que vous avez été affecté pour encadrer le projet suivant :\n\n"
                    + "Domaine : " + domaine + "\n"
                    + "Nom du projet : " + nomProjet + "\n"
                    + "Description : " + description + "\n"
                    + "Nom de l'entreprise : " + nomEntreprise + "\n"
                    + "Nous vous remercions pour votre implication et votre collaboration.\n\n"
                    + "Cordialement,\n"
                    + "Equipe de INSIGHT";

            message.setText(contenuMessage);


            // Envoyer le message
            Transport.send(message);

            // Afficher une confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("L'e-mail a été envoyé avec succès au professeur.");
            alert.show();
        } catch (MessagingException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Une erreur s'est produite lors de l'envoi de l'e-mail.");
            alert.show();
        }
    }
}

