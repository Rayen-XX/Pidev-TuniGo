package com.esprit.gu.controller;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.esprit.gu.entity.Scooter;
import com.esprit.gu.service.ServiceScooter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class GestionScooter {
    @FXML
    private TextField numeroScooterField;
    @FXML
    private TextField localisationScooterField;
    @FXML
    private TableView<Scooter> tableScooter;
    @FXML
    private TableColumn<Scooter, Integer> idColumn;
    @FXML
    private TableColumn<Scooter, String> numeroScooterColumn;
    @FXML
    private TableColumn<Scooter, String> localisationScooterColumn;
    @FXML
    private TableColumn<Scooter, Void> actionColumn;
    private final ObservableList<Scooter> scooterList = FXCollections.observableArrayList();
    private ServiceScooter serviceScooter = new ServiceScooter();

    public GestionScooter() throws Exception {
    }

    @FXML
    public void initialize() {
        this.idColumn.setCellValueFactory(new PropertyValueFactory("idScooter"));
        this.numeroScooterColumn.setCellValueFactory(new PropertyValueFactory("numeroScooter"));
        this.localisationScooterColumn.setCellValueFactory(new PropertyValueFactory("localisationScooter"));
        this.actionColumn.setCellFactory((param) -> {
            return new TableCell<Scooter, Void>() {
                private final Button modifyButton = new Button("Modifier");
                private final Button deleteButton = new Button("Supprimer");

                {
                    this.modifyButton.setOnAction((event) -> {
                        Scooter scooter = (Scooter)this.getTableView().getItems().get(this.getIndex());
                        GestionScooter.this.numeroScooterField.setText(scooter.getNumeroScooter());
                        GestionScooter.this.localisationScooterField.setText(scooter.getLocalisationScooter());
                        GestionScooter.this.scooterList.remove(scooter);
                        GestionScooter.this.serviceScooter.modifier(scooter);
                    });
                    this.deleteButton.setOnAction((event) -> {
                        Scooter scooter = (Scooter)this.getTableView().getItems().get(this.getIndex());
                        GestionScooter.this.scooterList.remove(scooter);
                        GestionScooter.this.serviceScooter.supprimer(scooter.getIdScooter());
                    });
                }

                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        this.setGraphic((Node)null);
                    } else {
                        this.setGraphic(new HBox(5.0, new Node[]{this.modifyButton, this.deleteButton}));
                    }

                }
            };
        });
        this.tableScooter.setItems(this.scooterList);
    }

    @FXML
    private void ajouterScooter(ActionEvent event) {
        String numero = this.numeroScooterField.getText();
        String localisation = this.localisationScooterField.getText();
        if (!numero.isEmpty() && !localisation.isEmpty()) {
            Scooter scooter = new Scooter(0, numero, localisation);
            this.scooterList.add(scooter);
            System.out.println("Scooter ajouté : " + String.valueOf(scooter));
            this.serviceScooter.ajouter(scooter);
            this.tableScooter.refresh();
            this.numeroScooterField.clear();
            this.localisationScooterField.clear();
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
