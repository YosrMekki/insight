package controles;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import models.Entreprise;
import services.EntrepriseService;

public class EditEntreprisePopup extends Dialog<Pair<Entreprise, String>> {

    private TextField nomField;
    private TextField adresseField;
    private TextField numTelField;
    private TextField dureeContratField;

    private EntrepriseService entrepriseService = new EntrepriseService();

    public EditEntreprisePopup(Entreprise initialEntreprise) {
        setTitle("Edit Enterprise");

        // Set the button types (Save and Cancel)
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        // Create the grid pane layout for the dialog content
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Add text fields for editing Enterprise information
        nomField = new TextField(initialEntreprise.getNom());
        adresseField = new TextField(initialEntreprise.getAdresse());
        numTelField = new TextField(String.valueOf(initialEntreprise.getNumTel()));

        // Add labels and text fields to the grid
        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Adresse:"), 0, 1);
        grid.add(adresseField, 1, 1);
        grid.add(new Label("Numéro de téléphone:"), 0, 2);
        grid.add(numTelField, 1, 2);


        // Set the dialog content
        getDialogPane().setContent(grid);

        // Convert the result to a pair when the Save button is clicked
        setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.APPLY) {
                // Retrieve values from fields
                String nom = nomField.getText();
                String adresse = adresseField.getText();
                int numTel = Integer.parseInt(numTelField.getText());

                // Create updated enterprise object
                Entreprise updatedEntreprise = new Entreprise(
                        initialEntreprise.getId(),
                        nom,
                        adresse,
                        numTel
                );

                // Return the updated enterprise and nom pair
                return new Pair<>(updatedEntreprise, nom);
            }
            return null;
        });
    }
}