package Controllers;

import entities.Train;
import Services.ServiceTrain;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class GestionTrain {

    @FXML
    private TextField numeroTrainField;

    @FXML
    private Button ajouterButton;

    @FXML
    private TableView<Train> tableTrain;

    @FXML
    private TableColumn<Train, Integer> idColumn;

    @FXML
    private TableColumn<Train, String> numeroTrainColumn;

    @FXML
    private TableColumn<Train, String> actionColumn;

    private ServiceTrain serviceTrain;
    private ObservableList<Train> trainList;

    public GestionTrain() {
        // Créez une instance de ServiceTrain ici
        serviceTrain = new ServiceTrain();
        trainList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        // Charger les données des trains
        chargerTrains();

        // Configurer les colonnes dans la TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idTrain"));
        numeroTrainColumn.setCellValueFactory(new PropertyValueFactory<>("numeroTrain"));

        // Définir les actions des boutons pour chaque ligne dans la colonne "Action"
        actionColumn.setCellFactory(param -> new ButtonCell());

        // Définir l'action du bouton d'ajout
        ajouterButton.setOnAction(this::ajouterTrain);
    }

    private void chargerTrains() {
        trainList.clear();
        trainList.addAll(serviceTrain.getAll());  // Utiliser la méthode ServiceTrain pour obtenir tous les trains
        tableTrain.setItems(trainList);
    }

    @FXML
    private void ajouterTrain(ActionEvent event) {
        String numeroTrain = numeroTrainField.getText();
        if (numeroTrain.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de train ne peut pas être vide !");
            return;
        }
        // Ajouter le nouveau train avec le ServiceTrain
        Train train = new Train(0, numeroTrain); // L'ID sera défini par la base de données (auto-incrémenté)
        serviceTrain.ajouter(train);  // Appeler la méthode ServiceTrain pour ajouter le train
        chargerTrains();  // Recharger la liste des trains
        showAlert(Alert.AlertType.INFORMATION, "Succès", "Train ajouté avec succès !");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Classe interne pour les boutons dans la colonne "Action"
    private class ButtonCell extends TableCell<Train, String> {
        private final Button modifyButton = new Button("Modifier");
        private final Button deleteButton = new Button("Supprimer");

        public ButtonCell() {
            modifyButton.setOnAction(event -> modifierTrain());
            deleteButton.setOnAction(event -> supprimerTrain());
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new HBox(10, modifyButton, deleteButton));
            }
        }

        private void modifierTrain() {
            Train selectedTrain = tableTrain.getSelectionModel().getSelectedItem();
            if (selectedTrain != null) {
                String nouveauNumeroTrain = numeroTrainField.getText();
                if (nouveauNumeroTrain.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le numéro de train ne peut pas être vide !");
                    return;
                }
                selectedTrain.setNumeroTrain(nouveauNumeroTrain);
                serviceTrain.modifier(selectedTrain);  // Utiliser la méthode ServiceTrain pour modifier le train
                chargerTrains();  // Recharger la liste des trains
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Train modifié avec succès !");
            }
        }

        private void supprimerTrain() {
            Train selectedTrain = tableTrain.getSelectionModel().getSelectedItem();
            if (selectedTrain != null) {
                serviceTrain.supprimer(selectedTrain.getIdTrain());  // Utiliser la méthode ServiceTrain pour supprimer le train
                chargerTrains();  // Recharger la liste des trains
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Train supprimé avec succès !");
            }
        }
    }
}
