package Controllers;

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
