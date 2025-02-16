package Controllers;

import entities.Scooter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import Services.ServiceScooter;

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

    @FXML
    public void initialize() {
        // Initialiser les colonnes de la table
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idScooter"));
        numeroScooterColumn.setCellValueFactory(new PropertyValueFactory<>("numeroScooter"));
        localisationScooterColumn.setCellValueFactory(new PropertyValueFactory<>("localisationScooter"));

        // Ajouter des boutons "Modifier" et "Supprimer" dans la colonne action
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button modifyButton = new Button("Modifier");
            private final Button deleteButton = new Button("Supprimer");

            {
                modifyButton.setOnAction(event -> {
                    Scooter scooter = getTableView().getItems().get(getIndex());
                    numeroScooterField.setText(scooter.getNumeroScooter());
                    localisationScooterField.setText(scooter.getLocalisationScooter());
                    scooterList.remove(scooter);
                    serviceScooter.modifier(scooter);  // Modifier le scooter dans la base de données
                });

                deleteButton.setOnAction(event -> {
                    Scooter scooter = getTableView().getItems().get(getIndex());
                    scooterList.remove(scooter);
                    serviceScooter.supprimer(scooter.getIdScooter());  // Supprimer le scooter de la base de données
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, modifyButton, deleteButton));
                }
            }
        });

        // Lier la liste des scooters à la TableView
        tableScooter.setItems(scooterList);
    }

    @FXML
    private void ajouterScooter(ActionEvent event) {
        String numero = numeroScooterField.getText();
        String localisation = localisationScooterField.getText();

        // Vérifier que les champs sont remplis
        if (numero.isEmpty() || localisation.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        // Créer un nouveau scooter
        Scooter scooter = new Scooter(0, numero, localisation);  // Id est 0, il sera généré par la base de données
        scooterList.add(scooter);

        // Afficher dans la console pour vérifier que le scooter est bien ajouté
        System.out.println("Scooter ajouté : " + scooter);

        // Ajouter le scooter à la base de données
        serviceScooter.ajouter(scooter);

        // Mettre à jour la TableView
        tableScooter.refresh();

        // Vider les champs de texte
        numeroScooterField.clear();
        localisationScooterField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
