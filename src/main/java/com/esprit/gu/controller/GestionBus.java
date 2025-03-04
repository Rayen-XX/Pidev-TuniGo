package com.esprit.gu.controller;

import com.esprit.gu.entity.Bus;
import com.esprit.gu.service.ServiceBus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class GestionBus {
    @FXML
    private TextField numeroBusField;
    @FXML
    private Button ajouterButton;
    @FXML
    private ListView<Bus> listViewBus;
    private ServiceBus serviceBus = new ServiceBus();
    private ObservableList<Bus> busList = FXCollections.observableArrayList();

    public GestionBus() throws Exception {
    }

    @FXML
    public void initialize() {
        this.chargerBus();
        this.listViewBus.setCellFactory((param) -> {
            return new BusCell();
        });
        this.ajouterButton.setOnAction(this::ajouterBus);
    }

    private void chargerBus() {
        this.busList.clear();
        this.busList.addAll(this.serviceBus.getAll());
        this.listViewBus.setItems(this.busList);
    }

    @FXML
    private void ajouterBus(ActionEvent event) {
        String numeroBus = this.numeroBusField.getText();
        if (numeroBus.isEmpty()) {
            this.showAlert(AlertType.ERROR, "Erreur", "Le numéro de bus ne peut pas être vide !");
        } else {
            Bus bus = new Bus(0, numeroBus);
            this.serviceBus.ajouter(bus);
            this.chargerBus();
            this.showAlert(AlertType.INFORMATION, "Succès", "Bus ajouté avec succès !");
        }
    }

    private void supprimerBus(Bus bus) {
        if (bus != null) {
            this.serviceBus.supprimer(bus.getIdBus());
            this.chargerBus();
            this.showAlert(AlertType.INFORMATION, "Succès", "Bus supprimé avec succès !");
        }

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class BusCell extends ListCell<Bus> {
        private final Label busLabel = new Label();
        private final Button deleteButton = new Button("Supprimer");
        private final HBox hbox = new HBox();

        public BusCell() {
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            this.deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
            this.deleteButton.setOnAction((event) -> {
                Bus bus = (Bus)this.getItem();
                if (bus != null) {
                    GestionBus.this.supprimerBus(bus);
                }

            });
            this.hbox.getChildren().addAll(new Node[]{this.busLabel, spacer, this.deleteButton});
            this.hbox.setSpacing(10.0);
        }

        protected void updateItem(Bus bus, boolean empty) {
            super.updateItem(bus, empty);
            if (!empty && bus != null) {
                this.busLabel.setText("Bus :     " + bus.getNumeroBus());
                this.setGraphic(this.hbox);
            } else {
                this.setGraphic((Node)null);
            }

        }
    }
}
