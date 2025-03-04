package com.esprit.gu.controller;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.esprit.gu.entity.Train;
import com.esprit.gu.service.ServiceTrain;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

public class GestionTrain {
    @FXML
    private TextField numeroTrainField;
    @FXML
    private Button ajouterButton;
    @FXML
    private ListView<Train> listViewTrain;
    private ServiceTrain serviceTrain = new ServiceTrain();
    private ObservableList<Train> trainList = FXCollections.observableArrayList();

    public GestionTrain() throws Exception {
    }

    @FXML
    public void initialize() {
        this.chargerTrains();
        this.listViewTrain.setCellFactory((param) -> {
            return new TrainListCell();
        });
        this.ajouterButton.setOnAction(this::ajouterTrain);
    }

    private void chargerTrains() {
        this.trainList.clear();
        this.trainList.addAll(this.serviceTrain.getAll());
        this.listViewTrain.setItems(this.trainList);
    }

    @FXML
    private void ajouterTrain(ActionEvent event) {
        String numeroTrain = this.numeroTrainField.getText();
        if (numeroTrain.isEmpty()) {
            this.showAlert(AlertType.ERROR, "Erreur", "Le numéro de train ne peut pas être vide !");
        } else {
            Train train = new Train(0, numeroTrain);
            this.serviceTrain.ajouter(train);
            this.chargerTrains();
            this.showAlert(AlertType.INFORMATION, "Succès", "Train ajouté avec succès !");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class TrainListCell extends ListCell<Train> {
        private final HBox hbox = new HBox();
        private final Text trainInfo = new Text();
        private final Button deleteButton = new Button("Supprimer");
        private final Region spacer = new Region();

        public TrainListCell() {
            this.deleteButton.setOnAction((event) -> {
                this.supprimerTrain((Train)this.getItem());
            });
            this.hbox.setSpacing(10.0);
            this.deleteButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
            this.hbox.getChildren().addAll(new Node[]{this.trainInfo, this.spacer, this.deleteButton});
            HBox.setHgrow(this.spacer, Priority.ALWAYS);
        }

        protected void updateItem(Train train, boolean empty) {
            super.updateItem(train, empty);
            if (!empty && train != null) {
                this.trainInfo.setText("Train N°: " + train.getNumeroTrain());
                this.setGraphic(this.hbox);
            } else {
                this.setGraphic((Node)null);
            }

        }

        private void supprimerTrain(Train train) {
            if (train != null) {
                GestionTrain.this.serviceTrain.supprimer(train.getIdTrain());
                GestionTrain.this.chargerTrains();
                GestionTrain.this.showAlert(AlertType.INFORMATION, "Succès", "Train supprimé avec succès !");
            }

        }
    }
}
