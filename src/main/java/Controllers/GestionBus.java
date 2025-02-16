package Controllers;

import entities.Bus;
import Services.ServiceBus;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class GestionBus {

    @FXML
    private TextField numeroBusField;

    @FXML
    private Button ajouterButton;

    @FXML
    private TableView<Bus> tableBus;

    @FXML
    private TableColumn<Bus, Integer> idColumn;

    @FXML
    private TableColumn<Bus, String> numeroBusColumn;

    @FXML
    private TableColumn<Bus, String> actionColumn;

    private ServiceBus serviceBus;
    private ObservableList<Bus> busList;

    public GestionBus() {
        serviceBus = new ServiceBus();
        busList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Charger les bus à partir de la base de données
        chargerBus();

        // Configurer les colonnes de la TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idBus"));
        numeroBusColumn.setCellValueFactory(new PropertyValueFactory<>("numeroBus"));

        // Ajouter les actions pour les boutons dans la colonne "Action"
        actionColumn.setCellFactory(param -> new ButtonCell());

        // Action pour le bouton "Ajouter"
        ajouterButton.setOnAction(this::ajouterBus);
    }

    private void chargerBus() {
        busList.clear();
        busList.addAll(serviceBus.getAll());  // Récupérer tous les bus depuis le service
        tableBus.setItems(busList);  // Mettre à jour la TableView
    }

    @FXML
    private void ajouterBus(ActionEvent event) {
        String numeroBus = numeroBusField.getText();
        if (numeroBus.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de bus ne peut pas être vide !");
            return;
        }
        Bus bus = new Bus(0, numeroBus); // ID auto-incrémenté par la base de données
        serviceBus.ajouter(bus);  // Ajouter le bus
        chargerBus();  // Recharger la liste des bus
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Bus ajouté avec succès !");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Classe interne pour les boutons dans la colonne "Action"
    private class ButtonCell extends TableCell<Bus, String> {
        private final Button deleteButton = new Button("Supprimer");

        public ButtonCell() {
            deleteButton.setOnAction(event -> supprimerBus());
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }

        private void supprimerBus() {
            Bus selectedBus = tableBus.getSelectionModel().getSelectedItem();
            if (selectedBus != null) {
                serviceBus.supprimer(selectedBus.getIdBus());  // Suppression via le service
                chargerBus();  // Recharger la liste des bus
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Bus supprimé avec succès !");
            }
        }
    }
}
