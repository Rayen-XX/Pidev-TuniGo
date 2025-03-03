package com.esprit.gu.controller;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import com.esprit.gu.entity.Taxi;
import com.esprit.gu.service.ServiceTaxi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class GestionTaxi {
    @FXML
    private TextField numeroTaxiField;
    @FXML
    private TextField numeroChauffeurField;
    @FXML
    private TextField prenomChauffeurField;
    @FXML
    private TextField nomChauffeurField;
    @FXML
    private Button ajouterButton;
    @FXML
    private ListView<Taxi> listViewTaxi;
    private ServiceTaxi serviceTaxi = new ServiceTaxi();
    private ObservableList<Taxi> taxiList = FXCollections.observableArrayList();
    private int taxiEnModification = -1;

    public GestionTaxi() throws Exception {
    }

    @FXML
    public void initialize() {
        this.chargerTaxis();
        this.ajouterButton.setOnAction(this::ajouterTaxi);
    }

    private void chargerTaxis() {
        this.taxiList.clear();
        this.taxiList.addAll(this.serviceTaxi.getAll());
        this.listViewTaxi.setItems(this.taxiList);
        this.listViewTaxi.setCellFactory(new Callback<ListView<Taxi>, ListCell<Taxi>>() {
            public ListCell<Taxi> call(ListView<Taxi> param) {
                return new ListCell<Taxi>() {
                    private final Text taxiInfo = new Text();
                    private final Button deleteButton = new Button("Supprimer");
                    private final Button updateButton = new Button("Modifier");
                    private final Button detailsButton = new Button("Détails");
                    private final Region spacer = new Region();
                    private final HBox hbox;

                    {
                        this.hbox = new HBox(new Node[]{this.taxiInfo, this.spacer, this.updateButton, this.deleteButton, this.detailsButton});
                        HBox.setHgrow(this.spacer, Priority.ALWAYS);
                    }

                    protected void updateItem(Taxi taxi, boolean empty) {
                        super.updateItem(taxi, empty);
                        if (!empty && taxi != null) {
                            Text var10000 = this.taxiInfo;
                            String var10001 = taxi.getNumeroTaxi();
                            var10000.setText("Taxi : " + var10001 + " - Chauffeur : " + taxi.getPrenomChauffeur() + " " + taxi.getNomChauffeur());
                            ImageView editIcon = new ImageView(new Image(getClass().getResource("/Images/update_icon.png").toExternalForm()));
                            ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/Images/delete_icon.png").toExternalForm()));
                            ImageView viewIcon = new ImageView(new Image(getClass().getResource("/Images/detail_icon.png").toExternalForm()));
                            editIcon.setFitWidth(24.0);
                            editIcon.setFitHeight(24.0);
                            deleteIcon.setFitWidth(24.0);
                            deleteIcon.setFitHeight(24.0);
                            viewIcon.setFitWidth(24.0);
                            viewIcon.setFitHeight(24.0);
                            HBox iconHBox = new HBox(10.0, new Node[]{editIcon, deleteIcon, viewIcon});
                            iconHBox.setAlignment(Pos.CENTER_RIGHT);
                            HBox.setHgrow(iconHBox, Priority.ALWAYS);
                            HBox rechbox = new HBox(20.0, new Node[]{this.taxiInfo, iconHBox});
                            HBox.setHgrow(this.taxiInfo, Priority.ALWAYS);
                            rechbox.setAlignment(Pos.CENTER_LEFT);
                            this.setGraphic(rechbox);
                            editIcon.setOnMouseClicked((event) -> {
                                GestionTaxi.this.modifierTaxi(taxi);
                            });
                            deleteIcon.setOnMouseClicked((event) -> {
                                GestionTaxi.this.supprimerTaxi(taxi);
                            });
                            viewIcon.setOnMouseClicked((event) -> {
                                GestionTaxi.this.afficherDetails(taxi);
                            });
                        } else {
                            this.setGraphic((Node)null);
                        }

                    }
                };
            }
        });
    }

    @FXML
    private void ajouterTaxi(ActionEvent event) {
        if (!this.numeroTaxiField.getText().isEmpty() && !this.numeroChauffeurField.getText().isEmpty() && !this.prenomChauffeurField.getText().isEmpty() && !this.nomChauffeurField.getText().isEmpty()) {
            Taxi newTaxi = new Taxi();
            newTaxi.setNumeroTaxi(this.numeroTaxiField.getText());
            newTaxi.setNumeroChauffeur(this.numeroChauffeurField.getText());
            newTaxi.setPrenomChauffeur(this.prenomChauffeurField.getText());
            newTaxi.setNomChauffeur(this.nomChauffeurField.getText());
            this.serviceTaxi.ajouter(newTaxi);
            this.chargerTaxis();
            this.showAlert(AlertType.INFORMATION, "Succès", "Taxi ajouté avec succès !");
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Tous les champs doivent être remplis !");
        }

    }

    private void supprimerTaxi(Taxi taxi) {
        if (taxi != null) {
            this.serviceTaxi.supprimer(taxi.getIdTaxi());
            this.chargerTaxis();
            this.showAlert(AlertType.INFORMATION, "Succès", "Taxi supprimé avec succès !");
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un taxi à supprimer !");
        }

    }

    private void modifierTaxi(Taxi taxi) {
        if (taxi != null) {
            Dialog<Taxi> dialog = new Dialog();
            dialog.setTitle("Modifier le Taxi");
            dialog.setHeaderText("Modifiez les informations du taxi");
            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(new ButtonType[]{saveButtonType, ButtonType.CANCEL});
            TextField numeroTaxiField = new TextField(taxi.getNumeroTaxi());
            TextField numeroChauffeurField = new TextField(taxi.getNumeroChauffeur());
            TextField prenomChauffeurField = new TextField(taxi.getPrenomChauffeur());
            TextField nomChauffeurField = new TextField(taxi.getNomChauffeur());
            VBox dialogContent = new VBox(10.0, new Node[]{numeroTaxiField, numeroChauffeurField, prenomChauffeurField, nomChauffeurField});
            dialog.getDialogPane().setContent(dialogContent);
            dialog.setResultConverter((dialogButton) -> {
                if (dialogButton == saveButtonType) {
                    taxi.setNumeroTaxi(numeroTaxiField.getText());
                    taxi.setNumeroChauffeur(numeroChauffeurField.getText());
                    taxi.setPrenomChauffeur(prenomChauffeurField.getText());
                    taxi.setNomChauffeur(nomChauffeurField.getText());
                    return taxi;
                } else {
                    return null;
                }
            });
            dialog.showAndWait().ifPresent((updatedTaxi) -> {
                this.serviceTaxi.modifier(updatedTaxi);
                this.chargerTaxis();
                this.showAlert(AlertType.INFORMATION, "Succès", "Taxi modifié avec succès !");
            });
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un taxi à modifier !");
        }

    }

    private void afficherDetails(Taxi taxi) {
        if (taxi != null) {
            int var10000 = taxi.getIdTaxi();
            String details = "ID: " + var10000 + "\nNuméro: " + taxi.getNumeroTaxi() + "\n Prenom Chauffeur: " + taxi.getPrenomChauffeur() + "\n Nom Chauffeur: " + taxi.getNomChauffeur();
            this.showAlert(AlertType.INFORMATION, "Détails du Taxi", details);
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un taxi à afficher !");
        }

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
