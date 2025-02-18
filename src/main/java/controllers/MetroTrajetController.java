package controllers;

import entities.MetroTrajet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceMetro;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MetroTrajetController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableView<MetroTrajet> table;

    @FXML
    private TableColumn<MetroTrajet, Integer> colId;

    @FXML
    private TableColumn<MetroTrajet, String> colGdepart;

    @FXML
    private TableColumn<MetroTrajet, String> colGarrive;

    @FXML
    private TableColumn<MetroTrajet, String> colHdepart;

    @FXML
    private TableColumn<MetroTrajet, String> colHarive;

    @FXML
    private TextField tfGarrive;

    @FXML
    private TextField tfGdepart;

    @FXML
    private TextField tfHarive;

    @FXML
    private TextField tfHdepart;

    private ServiceMetro serviceMetro = new ServiceMetro();

    @FXML
    void ajouterTrajetmetro(ActionEvent event) {
        if (validateInputs()) {
            MetroTrajet trajet = new MetroTrajet(
                    tfGdepart.getText(),
                    tfGarrive.getText(),
                    tfHdepart.getText(),
                    tfHarive.getText()
            );

            if (serviceMetro.isUnique(trajet)) {
                serviceMetro.ajouter(trajet);
                loadTableData();
                clearFields();
            } else {
                showAlert("Erreur", "Ce trajet existe déjà !");
            }
        }
    }

    @FXML
    void annulerTrajetmetro(ActionEvent event) {
        clearFields();
    }

    @FXML
    void modifierTrajetmetro(ActionEvent event) {
        MetroTrajet selected = table.getSelectionModel().getSelectedItem();
        if (selected != null && validateInputs()) {
            selected.setGareDepart(tfGdepart.getText());
            selected.setGareArrivee(tfGarrive.getText());
            selected.setHeureDepart(tfHdepart.getText());
            selected.setHeureArrivee(tfHarive.getText());

            serviceMetro.modifier(selected);
            loadTableData();
            clearFields();
        }
    }

    @FXML
    void supprimerTrajetmetro(ActionEvent event) {
        MetroTrajet selected = table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            serviceMetro.supprimer(selected.getId());
            loadTableData();
        }
    }

    @FXML
    void initialize() {
        // Liaison des colonnes du TableView
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colGdepart.setCellValueFactory(new PropertyValueFactory<>("gareDepart"));
        colGarrive.setCellValueFactory(new PropertyValueFactory<>("gareArrivee"));
        colHdepart.setCellValueFactory(new PropertyValueFactory<>("heureDepart"));
        colHarive.setCellValueFactory(new PropertyValueFactory<>("heureArrivee"));

        // Charger les données dans le TableView
        loadTableData();
    }

    private void loadTableData() {
        ObservableList<MetroTrajet> trajets = FXCollections.observableArrayList(serviceMetro.getAll());
        table.setItems(trajets);
    }

    private boolean validateInputs() {
        if (tfGdepart.getText().isEmpty() || tfGarrive.getText().isEmpty() ||
                tfHdepart.getText().isEmpty() || tfHarive.getText().isEmpty()) {
            showAlert("Erreur", "Tous les champs sont obligatoires !");
            return false;
        }
        if (!tfHdepart.getText().matches("^([0-1]?\\d|2[0-3]):[0-5]\\d$") ||
                !tfHarive.getText().matches("^([0-1]?\\d|2[0-3]):[0-5]\\d$")) {
            showAlert("Erreur", "Format d'heure invalide (HH:MM) !");
            return false;
        }
        return true;
    }

    private void clearFields() {
        tfGdepart.clear();
        tfGarrive.clear();
        tfHdepart.clear();
        tfHarive.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}