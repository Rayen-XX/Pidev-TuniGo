package Controllers;

import Services.ServiceMetro;
import entities.Metro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.scene.control.TableCell;

import java.util.List;

public class GestionMetro {

    @FXML
    private TextField numeroMetroField;

    @FXML
    private Button ajouterButton;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button modifierButton;

    @FXML
    private TableView<Metro> tableMetro;

    @FXML
    private TableColumn<Metro, Integer> idColumn;

    @FXML
    private TableColumn<Metro, String> numeroMetroColumn;

    @FXML
    private TableColumn<Metro, Void> actionColumn; // Nouvelle colonne d'action

    private ServiceMetro serviceMetro;

    public GestionMetro() {
        serviceMetro = new ServiceMetro();
    }

    @FXML
    public void initialize() {
        // Charger les métros à partir de la base de données
        loadData();

        // Configurer les colonnes de la TableView
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idMetro"));
        numeroMetroColumn.setCellValueFactory(new PropertyValueFactory<>("numeroMetro"));

        // Créer la colonne d'action avec des boutons
        actionColumn.setCellFactory(new Callback<TableColumn<Metro, Void>, TableCell<Metro, Void>>() {
            @Override
            public TableCell<Metro, Void> call(TableColumn<Metro, Void> param) {
                return new TableCell<Metro, Void>() {
                    private final Button modifierButton = new Button("Modifier");
                    private final Button supprimerButton = new Button("Supprimer");

                    {
                        modifierButton.setOnAction(event -> {
                            Metro metro = getTableView().getItems().get(getIndex());
                            modifierMetro(metro);
                        });

                        supprimerButton.setOnAction(event -> {
                            Metro metro = getTableView().getItems().get(getIndex());
                            supprimerMetro(metro);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(new javafx.scene.layout.HBox(modifierButton, supprimerButton));  // Ajouter les deux boutons dans la cellule
                        }
                    }
                };
            }
        });

        // Action pour le bouton "Ajouter"
        ajouterButton.setOnAction(this::ajouterMetro);
    }

    // Charger tous les métros dans la table
    private void loadData() {
        List<Metro> metros = serviceMetro.getAll();
        tableMetro.getItems().setAll(metros);
    }

    // Ajouter un métro
    @FXML
    private void ajouterMetro(ActionEvent event) {
        String numeroMetro = numeroMetroField.getText();

        if (numeroMetro.isEmpty()) {
            showAlert(AlertType.ERROR, "Erreur", "Le numéro du métro ne peut pas être vide !");
        } else {
            Metro metro = new Metro();
            metro.setNumeroMetro(numeroMetro);
            serviceMetro.ajouter(metro);
            loadData();
            showAlert(AlertType.INFORMATION, "Succès", "Métro ajouté avec succès !");
            numeroMetroField.clear();
        }
    }

    // Supprimer un métro
    private void supprimerMetro(Metro metro) {
        if (metro != null) {
            serviceMetro.supprimer(metro.getIdMetro());
            loadData();
            showAlert(AlertType.INFORMATION, "Succès", "Métro supprimé avec succès !");
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un métro à supprimer !");
        }
    }

    // Modifier un métro
    private void modifierMetro(Metro metro) {
        if (metro != null) {
            String numeroMetro = numeroMetroField.getText();
            if (numeroMetro.isEmpty()) {
                showAlert(AlertType.ERROR, "Erreur", "Le numéro du métro ne peut pas être vide !");
            } else {
                metro.setNumeroMetro(numeroMetro);
                serviceMetro.modifier(metro);
                loadData();
                showAlert(AlertType.INFORMATION, "Succès", "Métro modifié avec succès !");
                numeroMetroField.clear();
            }
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un métro à modifier !");
        }
    }

    // Afficher une alerte
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
