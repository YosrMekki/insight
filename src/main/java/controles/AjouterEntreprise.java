package controles;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.Entreprise;
import services.EntrepriseService;

import java.sql.SQLException;

public class AjouterEntreprise {
        @FXML
        private TextField nomFX;

        @FXML
        private TextField adresseFX;

        @FXML
        private TextField num_telFX;


        @FXML
        private Button valider;

        @FXML
        void addEntreprise() {
            String nom = nomFX.getText();
            String adresse = adresseFX.getText();
            String numTelText = num_telFX.getText();

            // Vérification de la validité des champs
            if (nom.isEmpty() || adresse.isEmpty() || numTelText.isEmpty()) {
                showAlert("Tous les champs doivent être remplis.");
                return;
            }

            // Vérification de la validité du numéro de téléphone (8 chiffres)
            if (!numTelText.matches("\\d{8}")) {
                showAlert("Le numéro de téléphone doit être un nombre de 8 chiffres.");
                return;
            }

            // Conversion du numéro de téléphone et de la durée du contrat en int
            int numTel = Integer.parseInt(numTelText);

            EntrepriseService entrepriseService = new EntrepriseService();
            Entreprise nouvelleEntreprise = new Entreprise(nom, adresse, numTel);
            try {
                entrepriseService.ajouter(nouvelleEntreprise);
                // Si l'ajout est réussi, afficher un message de succès
                showAlert("Entreprise ajoutée avec succès.");
            } catch (SQLException e) {
                // En cas d'erreur, afficher un message d'erreur
                showAlert("Erreur lors de l'ajout de l'entreprise.");
            }

            // Afficher un message de succès
            showAlert("Entreprise ajoutée avec succès.");
        }

        // Méthode utilitaire pour afficher une alerte
        private void showAlert(String message) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }

