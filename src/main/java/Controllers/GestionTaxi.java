/*package Controllers;

import entities.Taxi;
import Services.ServiceTaxi;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class GestionTaxi {

    @FXML
    private TextField numeroTaxiField, numeroChauffeurField, prenomChauffeurField, nomChauffeurField;

    @FXML
    private Button ajouterButton, modifierButton; // Bouton pour enregistrer la modification

    @FXML
    private TableView<Taxi> tableTaxi;

    @FXML
    private TableColumn<Taxi, Integer> idColumn;
    @FXML
    private TableColumn<Taxi, String> numeroTaxiColumn, numeroChauffeurColumn, prenomChauffeurColumn, nomChauffeurColumn;
    @FXML
    private TableColumn<Taxi, String> actionColumn;

    private ServiceTaxi serviceTaxi;
    private ObservableList<Taxi> taxiList;

    private int taxiEnModification = -1; // Stocke l'ID du taxi en modification (-1 si aucun)

    public GestionTaxi() {
        serviceTaxi = new ServiceTaxi();
        taxiList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        chargerTaxis();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTaxi"));
        numeroTaxiColumn.setCellValueFactory(new PropertyValueFactory<>("numeroTaxi"));
        numeroChauffeurColumn.setCellValueFactory(new PropertyValueFactory<>("numeroChauffeur"));
        prenomChauffeurColumn.setCellValueFactory(new PropertyValueFactory<>("prenomChauffeur"));
        nomChauffeurColumn.setCellValueFactory(new PropertyValueFactory<>("nomChauffeur"));

        actionColumn.setCellFactory(param -> new ButtonCell());

        ajouterButton.setOnAction(this::ajouterTaxi);
        modifierButton.setOnAction(this::modifierTaxiDansBD); // Associe la modification au bouton

        modifierButton.setVisible(false); // Cache "Confirmer Modification" au démarrage
    }

    private void chargerTaxis() {
        taxiList.clear();
        taxiList.addAll(serviceTaxi.getAll());
        tableTaxi.setItems(taxiList);
    }
/*
    @FXML
    private void ajouterTaxi(ActionEvent event) {
        if (numeroTaxiField.getText().isEmpty() || numeroChauffeurField.getText().isEmpty() ||
                prenomChauffeurField.getText().isEmpty() || nomChauffeurField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        Taxi taxi = new Taxi(0, numeroTaxiField.getText(), numeroChauffeurField.getText(),
                prenomChauffeurField.getText(), nomChauffeurField.getText());
        serviceTaxi.ajouter(taxi);
        chargerTaxis();
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Taxi ajouté avec succès !");
        viderChamps();
    }

    @FXML
    private void modifierTaxiDansBD(ActionEvent event) {
        if (taxiEnModification == -1) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun taxi sélectionné pour modification !");
            return;
        }

        if (numeroTaxiField.getText().isEmpty() || numeroChauffeurField.getText().isEmpty() ||
                prenomChauffeurField.getText().isEmpty() || nomChauffeurField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        Taxi taxiModifie = new Taxi(taxiEnModification, numeroTaxiField.getText(), numeroChauffeurField.getText(),
                prenomChauffeurField.getText(), nomChauffeurField.getText());

        serviceTaxi.modifier(taxiModifie); // Met à jour la base de données
        chargerTaxis();
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Modification enregistrée avec succès !");

        taxiEnModification = -1; // Réinitialisation du mode modification
        ajouterButton.setVisible(true); // Affiche à nouveau le bouton "Ajouter"
        modifierButton.setVisible(false); // Cache "Confirmer Modification"
        viderChamps();
    }
*/
/*
@FXML
private void ajouterTaxi(ActionEvent event) {
    if (numeroTaxiField.getText().isEmpty() || numeroChauffeurField.getText().isEmpty() ||
            prenomChauffeurField.getText().isEmpty() || nomChauffeurField.getText().isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
        return;
    }

    if (!isValidName(prenomChauffeurField.getText()) || !isValidName(nomChauffeurField.getText())) {
        showAlert(Alert.AlertType.ERROR, "Erreur", "Le prénom et le nom ne peuvent pas contenir de chiffres !");
        return;
    }

    if (!isValidNumber(numeroTaxiField.getText()) || !isValidNumber(numeroChauffeurField.getText())) {
        showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de taxi et le numéro de chauffeur ne peuvent contenir que des chiffres !");
        return;
    }

    Taxi taxi = new Taxi(0, numeroTaxiField.getText(), numeroChauffeurField.getText(),
            prenomChauffeurField.getText(), nomChauffeurField.getText());
    serviceTaxi.ajouter(taxi);
    chargerTaxis();
    showAlert(Alert.AlertType.INFORMATION, "Succès", "Taxi ajouté avec succès !");
    viderChamps();
}

    @FXML
    private void modifierTaxiDansBD(ActionEvent event) {
        if (taxiEnModification == -1) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun taxi sélectionné pour modification !");
            return;
        }

        if (numeroTaxiField.getText().isEmpty() || numeroChauffeurField.getText().isEmpty() ||
                prenomChauffeurField.getText().isEmpty() || nomChauffeurField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        if (!isValidName(prenomChauffeurField.getText()) || !isValidName(nomChauffeurField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le prénom et le nom ne peuvent pas contenir de chiffres !");
            return;
        }

        if (!isValidNumber(numeroTaxiField.getText()) || !isValidNumber(numeroChauffeurField.getText())) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de taxi et le numéro de chauffeur ne peuvent contenir que des chiffres !");
            return;
        }

        Taxi taxiModifie = new Taxi(taxiEnModification, numeroTaxiField.getText(), numeroChauffeurField.getText(),
                prenomChauffeurField.getText(), nomChauffeurField.getText());

        serviceTaxi.modifier(taxiModifie); // Met à jour la base de données
        chargerTaxis();
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Modification enregistrée avec succès !");

        taxiEnModification = -1; // Réinitialisation du mode modification
        ajouterButton.setVisible(true); // Affiche à nouveau le bouton "Ajouter"
        modifierButton.setVisible(false); // Cache "Confirmer Modification"
        viderChamps();
    }

    // Méthode pour vérifier si le nom ou prénom contient des chiffres
    private boolean isValidName(String name) {
        return name != null && !name.matches(".*\\d.*"); // Vérifie si le nom ne contient pas de chiffres
    }

    // Méthode pour vérifier si le numéro contient des lettres
    private boolean isValidNumber(String number) {
        return number != null && number.matches("[0-9]+"); // Vérifie si le numéro contient uniquement des chiffres
    }

    private void viderChamps() {
        numeroTaxiField.clear();
        numeroChauffeurField.clear();
        prenomChauffeurField.clear();
        nomChauffeurField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class ButtonCell extends TableCell<Taxi, String> {
        private final Button modifyButton = new Button();
        private final Button deleteButton = new Button();
        private final Button detailsButton = new Button();

        public ButtonCell() {
            modifyButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/Images/edit_icon.png"), 16, 16, true, true)));
            deleteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/Images/delete_icon.png"), 16, 16, true, true)));
            detailsButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/Images/detail_icon.png"), 16, 16, true, true)));

            modifyButton.setOnAction(event -> modifierTaxi());
            deleteButton.setOnAction(event -> supprimerTaxi());
            detailsButton.setOnAction(event -> afficherDetailsTaxi());
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new HBox(10, modifyButton, deleteButton, detailsButton));
            }
        }

        private void modifierTaxi() {
            Taxi selectedTaxi = getTableView().getItems().get(getIndex());
            if (selectedTaxi != null) {
                numeroTaxiField.setText(selectedTaxi.getNumeroTaxi());
                numeroChauffeurField.setText(selectedTaxi.getNumeroChauffeur());
                prenomChauffeurField.setText(selectedTaxi.getPrenomChauffeur());
                nomChauffeurField.setText(selectedTaxi.getNomChauffeur());

                taxiEnModification = selectedTaxi.getIdTaxi(); // Stocke l'ID en modification
                ajouterButton.setVisible(false);
                modifierButton.setVisible(true);
            }
        }

        private void supprimerTaxi() {
            Taxi selectedTaxi = getTableView().getItems().get(getIndex());
            if (selectedTaxi != null) {
                serviceTaxi.supprimer(selectedTaxi.getIdTaxi());
                chargerTaxis();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Taxi supprimé avec succès !");
            }
        }

        private void afficherDetailsTaxi() {
            Taxi selectedTaxi = getTableView().getItems().get(getIndex());
            if (selectedTaxi != null) {
                showAlert(Alert.AlertType.INFORMATION, "Détails du Taxi",
                        "Numéro Taxi: " + selectedTaxi.getNumeroTaxi() + "\n" +
                                "Numéro Chauffeur: " + selectedTaxi.getNumeroChauffeur() + "\n" +
                                "Prénom Chauffeur: " + selectedTaxi.getPrenomChauffeur() + "\n" +
                                "Nom Chauffeur: " + selectedTaxi.getNomChauffeur());
            }
        }
    }
}
*/
package Controllers;

import entities.Taxi;
import Services.ServiceTaxi;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.geometry.Pos;
import javafx.util.Callback;

public class GestionTaxi {

    @FXML
    private TextField numeroTaxiField, numeroChauffeurField, prenomChauffeurField, nomChauffeurField;

    @FXML
    private Button ajouterButton;

    @FXML
    private ListView<Taxi> listViewTaxi;

    private ServiceTaxi serviceTaxi;
    private ObservableList<Taxi> taxiList;

    private int taxiEnModification = -1; // Store taxi ID for modification (-1 if none)

    public GestionTaxi() {
        serviceTaxi = new ServiceTaxi();
        taxiList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        chargerTaxis();
        ajouterButton.setOnAction(this::ajouterTaxi);
    }

    private void chargerTaxis() {
        taxiList.clear();
        taxiList.addAll(serviceTaxi.getAll());
        listViewTaxi.setItems(taxiList);

        // Define how each item in the ListView is displayed
        listViewTaxi.setCellFactory(new Callback<ListView<Taxi>, ListCell<Taxi>>() {
            @Override
            public ListCell<Taxi> call(ListView<Taxi> param) {
                return new ListCell<Taxi>() {
                    private final Text taxiInfo = new Text();
                    // (Optional: you can remove these buttons if you use only icons)
                    private final Button deleteButton = new Button("Supprimer");
                    private final Button updateButton = new Button("Modifier");
                    private final Button detailsButton = new Button("Détails");
                    private final Region spacer = new Region();
                    // This HBox is used in the default button layout (not used in our icon layout)
                    private final HBox hbox = new HBox(taxiInfo, spacer, updateButton, deleteButton, detailsButton);

                    {
                        // Set dynamic spacing for the default layout
                        HBox.setHgrow(spacer, Priority.ALWAYS);

                        // These actions will not be used when we use our icon layout,
                        // but you may keep them if you plan to support both layouts.
                        deleteButton.setOnAction(event -> supprimerTaxi(getItem()));
                        updateButton.setOnAction(event -> modifierTaxi(getItem()));
                        detailsButton.setOnAction(event -> afficherDetails(getItem()));

                        // Styling the buttons (if used)
                        deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                        updateButton.setStyle("-fx-background-color: #f0ad4e; -fx-text-fill: white;");
                        detailsButton.setStyle("-fx-background-color: #5bc0de; -fx-text-fill: white;");
                    }

                    @Override
                    protected void updateItem(Taxi taxi, boolean empty) {
                        super.updateItem(taxi, empty);
                        if (empty || taxi == null) {
                            setGraphic(null);
                        } else {
                            // Set the taxi information text
                            taxiInfo.setText("Taxi : " + taxi.getNumeroTaxi() + " - Chauffeur : "
                                    + taxi.getPrenomChauffeur() + " " + taxi.getNomChauffeur());

                            // Create ImageViews for icons
                            ImageView editIcon = new ImageView(new Image("/Images/update_icon.png"));
                            ImageView deleteIcon = new ImageView(new Image("/Images/delete_icon.png"));
                            ImageView viewIcon = new ImageView(new Image("/Images/detail_icon.png"));

                            // Resize icons if necessary
                            editIcon.setFitWidth(24);
                            editIcon.setFitHeight(24);
                            deleteIcon.setFitWidth(24);
                            deleteIcon.setFitHeight(24);
                            viewIcon.setFitWidth(24);
                            viewIcon.setFitHeight(24);

                            // Create an HBox for the icons on the right
                            HBox iconHBox = new HBox(10,editIcon, deleteIcon, viewIcon);
                            iconHBox.setAlignment(Pos.CENTER_RIGHT);
                            HBox.setHgrow(iconHBox, Priority.ALWAYS);

                            // Create a parent HBox: taxi info on the left and icons on the right
                            HBox rechbox = new HBox(20, taxiInfo, iconHBox);
                            HBox.setHgrow(taxiInfo, Priority.ALWAYS);
                            rechbox.setAlignment(Pos.CENTER_LEFT);

                            // Set the HBox as the graphic for this cell
                            setGraphic(rechbox);

                            // Set actions for the icons
                            editIcon.setOnMouseClicked(event -> modifierTaxi(taxi));
                            deleteIcon.setOnMouseClicked(event -> supprimerTaxi(taxi));
                            viewIcon.setOnMouseClicked(event -> afficherDetails(taxi));
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void ajouterTaxi(ActionEvent event) {
        if (numeroTaxiField.getText().isEmpty() || numeroChauffeurField.getText().isEmpty() ||
                prenomChauffeurField.getText().isEmpty() || nomChauffeurField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
        } else {
            Taxi newTaxi = new Taxi();
            newTaxi.setNumeroTaxi(numeroTaxiField.getText());
            newTaxi.setNumeroChauffeur(numeroChauffeurField.getText());
            newTaxi.setPrenomChauffeur(prenomChauffeurField.getText());
            newTaxi.setNomChauffeur(nomChauffeurField.getText());

            serviceTaxi.ajouter(newTaxi);
            chargerTaxis(); // Refresh the ListView
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Taxi ajouté avec succès !");
        }
    }

    private void supprimerTaxi(Taxi taxi) {
        if (taxi != null) {
            serviceTaxi.supprimer(taxi.getIdTaxi());
            chargerTaxis(); // Refresh the ListView
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Taxi supprimé avec succès !");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un taxi à supprimer !");
        }
    }

    private void modifierTaxi(Taxi taxi) {
        if (taxi != null) {
            // Create a dialog for editing the taxi details
            Dialog<Taxi> dialog = new Dialog<>();
            dialog.setTitle("Modifier le Taxi");
            dialog.setHeaderText("Modifiez les informations du taxi");

            // Define dialog buttons
            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Create form fields pre-filled with taxi data
            TextField numeroTaxiField = new TextField(taxi.getNumeroTaxi());
            TextField numeroChauffeurField = new TextField(taxi.getNumeroChauffeur());
            TextField prenomChauffeurField = new TextField(taxi.getPrenomChauffeur());
            TextField nomChauffeurField = new TextField(taxi.getNomChauffeur());

            // Layout the form in a VBox with 10px spacing
            VBox dialogContent = new VBox(10, numeroTaxiField, numeroChauffeurField, prenomChauffeurField, nomChauffeurField);
            dialog.getDialogPane().setContent(dialogContent);

            // Convert the result when "Enregistrer" is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    taxi.setNumeroTaxi(numeroTaxiField.getText());
                    taxi.setNumeroChauffeur(numeroChauffeurField.getText());
                    taxi.setPrenomChauffeur(prenomChauffeurField.getText());
                    taxi.setNomChauffeur(nomChauffeurField.getText());
                    return taxi;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedTaxi -> {
                serviceTaxi.modifier(updatedTaxi); // Update the taxi via the service
                chargerTaxis(); // Refresh the ListView
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Taxi modifié avec succès !");
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un taxi à modifier !");
        }
    }

    private void afficherDetails(Taxi taxi) {
        if (taxi != null) {
            String details = "ID: " + taxi.getIdTaxi() + "\n"
                    + "Numéro: " + taxi.getNumeroTaxi() + "\n"
                    + "Chauffeur: " + taxi.getPrenomChauffeur() + " " + taxi.getNomChauffeur();
            showAlert(Alert.AlertType.INFORMATION, "Détails du Taxi", details);
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un taxi à afficher !");
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
