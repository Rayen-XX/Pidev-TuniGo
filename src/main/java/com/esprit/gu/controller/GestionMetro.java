package com.esprit.gu.controller;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.util.List;

import com.esprit.gu.entity.Metro;
import com.esprit.gu.service.ServiceMetro;
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

public class GestionMetro {
    @FXML
    private TextField numeroMetroField;
    @FXML
    private Button ajouterButton;
    @FXML
    private ListView<Metro> listViewMetro;
    private ServiceMetro serviceMetro = new ServiceMetro();

    public GestionMetro() throws Exception {
    }

    @FXML
    public void initialize() {
        this.loadData();
        this.listViewMetro.setCellFactory((param) -> {
            return new ListCell<Metro>() {
                private final Label numeroLabel = new Label();
                private final Button supprimerButton = new Button("Supprimer");
                private final Region spacer = new Region();
                private final HBox hbox;

                {
                    this.hbox = new HBox(new Node[]{this.numeroLabel, this.spacer, this.supprimerButton});
                    HBox.setHgrow(this.spacer, Priority.ALWAYS);
                    this.supprimerButton.setOnAction((event) -> {
                        Metro metro = (Metro)this.getItem();
                        if (metro != null) {
                            GestionMetro.this.supprimerMetro(metro);
                        }

                    });
                    this.hbox.setSpacing(10.0);
                    this.supprimerButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                }

                protected void updateItem(Metro metro, boolean empty) {
                    super.updateItem(metro, empty);
                    if (!empty && metro != null) {
                        this.numeroLabel.setText("Metro : " + metro.getNumeroMetro());
                        this.setGraphic(this.hbox);
                    } else {
                        this.setGraphic((Node)null);
                    }

                }
            };
        });
        this.ajouterButton.setOnAction(this::ajouterMetro);
    }

    private void loadData() {
        List<Metro> metros = this.serviceMetro.getAll();
        this.listViewMetro.getItems().setAll(metros);
    }

    @FXML
    private void ajouterMetro(ActionEvent event) {
        String numeroMetro = this.numeroMetroField.getText();
        if (numeroMetro.isEmpty()) {
            this.showAlert(AlertType.ERROR, "Erreur", "Le numéro du métro ne peut pas être vide !");
        } else {
            Metro metro = new Metro();
            metro.setNumeroMetro(numeroMetro);
            this.serviceMetro.ajouter(metro);
            this.loadData();
            this.showAlert(AlertType.INFORMATION, "Succès", "Métro ajouté avec succès !");
            this.numeroMetroField.clear();
        }

    }

    private void supprimerMetro(Metro metro) {
        if (metro != null) {
            this.serviceMetro.supprimer(metro.getIdMetro());
            this.loadData();
            this.showAlert(AlertType.INFORMATION, "Succès", "Métro supprimé avec succès !");
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un métro à supprimer !");
        }

    }

    private void modifierMetro(Metro metro) {
        if (metro != null) {
            String numeroMetro = this.numeroMetroField.getText();
            if (numeroMetro.isEmpty()) {
                this.showAlert(AlertType.ERROR, "Erreur", "Le numéro du métro ne peut pas être vide !");
            } else {
                metro.setNumeroMetro(numeroMetro);
                this.serviceMetro.modifier(metro);
                this.loadData();
                this.showAlert(AlertType.INFORMATION, "Succès", "Métro modifié avec succès !");
                this.numeroMetroField.clear();
            }
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un métro à modifier !");
        }

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
