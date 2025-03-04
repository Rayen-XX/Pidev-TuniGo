package com.esprit.gu.controller;

import com.esprit.gu.entity.Scooter;
import com.esprit.gu.service.ServiceScooter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class GestionScooter {
    @FXML
    private TextField searchField;
    @FXML
    private TextField numeroScooterField;
    @FXML
    private TextField localisationScooterField;
    @FXML
    private Button ajouterButton;
    @FXML
    private ListView<Scooter> listViewScooter;
    private final ObservableList<Scooter> scooterList = FXCollections.observableArrayList();
    private ServiceScooter serviceScooter = new ServiceScooter();

    public GestionScooter() throws Exception {
    }

    @FXML
    public void initialize() {
        // Initialisation du ListView
        //this.listViewScooter.setItems(scooterList);
        this.chargerScooters();
        this.ajouterButton.setOnAction(this::ajouterScooter);

        loadScooters();  // Call the method to load existing scooters
    }
    private void chargerScooters() {
        this.scooterList.clear();
        this.scooterList.addAll(this.serviceScooter.getAll());
        this.listViewScooter.setItems(this.scooterList);
        this.listViewScooter.setCellFactory(new Callback<ListView<Scooter>, ListCell<Scooter>>() {
            public ListCell<Scooter> call(ListView<Scooter> param) {
                return new ListCell<Scooter>() {
                    private final Text scooterInfo = new Text();
                    private final Button deleteButton = new Button("Supprimer");
                    private final Button updateButton = new Button("Modifier");
                    private final Button detailsButton = new Button("Détails");
                    private final Region spacer = new Region();
                    private final HBox hbox;

                    {
                        this.hbox = new HBox(new Node[]{this.scooterInfo, this.spacer, this.updateButton, this.deleteButton, this.detailsButton});
                        HBox.setHgrow(this.spacer, Priority.ALWAYS);
                    }

                    protected void updateItem(Scooter scooter, boolean empty) {
                        super.updateItem(scooter, empty);
                        if (!empty && scooter != null) {
                            // Affichage des informations du scooter
                            this.scooterInfo.setText("Scooter : " + scooter.getNumeroScooter() + " - Localisation : " + scooter.getLocalisationScooter());

                            // Chargement des icônes pour les actions
                            ImageView editIcon = loadImage("/views/GestionMoyTrans/Images/update_icon.png");
                            ImageView deleteIcon = loadImage("/views/GestionMoyTrans/Images/delete_icon.png");
                            ImageView viewIcon = loadImage("/views/GestionMoyTrans/Images/detail_icon.png");

                            // Définition des tailles des icônes
                            editIcon.setFitWidth(24.0);
                            editIcon.setFitHeight(24.0);
                            deleteIcon.setFitWidth(24.0);
                            deleteIcon.setFitHeight(24.0);
                            viewIcon.setFitWidth(24.0);
                            viewIcon.setFitHeight(24.0);

                            // Création du conteneur des icônes
                            HBox iconHBox = new HBox(10.0, new Node[]{editIcon, deleteIcon, viewIcon});
                            iconHBox.setAlignment(Pos.CENTER_RIGHT);
                            HBox.setHgrow(iconHBox, Priority.ALWAYS);

                            // Conteneur principal avec le texte et les icônes
                            HBox mainHBox = new HBox(20.0, new Node[]{this.scooterInfo, iconHBox});
                            HBox.setHgrow(this.scooterInfo, Priority.ALWAYS);
                            mainHBox.setAlignment(Pos.CENTER_LEFT);

                            // Définir la cellule graphique
                            this.setGraphic(mainHBox);

                            // Actions sur les icônes
                            editIcon.setOnMouseClicked((event) -> {
                                GestionScooter.this.modifierScooter(scooter);
                            });
                            deleteIcon.setOnMouseClicked((event) -> {
                                GestionScooter.this.supprimerScooter(scooter);
                            });
                            viewIcon.setOnMouseClicked((event) -> {
                                GestionScooter.this.afficherDetails(scooter);
                            });
                        } else {
                            this.setGraphic(null); // Si la cellule est vide, ne rien afficher
                        }
                    }

                    private ImageView loadImage(String path) {
                        try {
                            return new ImageView(new Image(getClass().getResource(path).toExternalForm()));
                        } catch (NullPointerException e) {
                            // Log or handle the exception if the image path is incorrect
                            System.out.println("Image not found at path: " + path);
                            return new ImageView();
                        }
                    }

                };
            }
        });
    }

    @FXML
    private void ajouterScooter(ActionEvent event) {
        String numeroScooter = this.numeroScooterField.getText();
        String localisationScooter = this.localisationScooterField.getText();

        if (!numeroScooter.isEmpty() && !localisationScooter.isEmpty()) {
            // Vérifier si le numéro de scooter existe déjà dans la base de données ou la liste
            if (isNumeroScooterUnique(numeroScooter)) {
                // Créer un nouvel objet Scooter
                Scooter scooter = new Scooter();
                scooter.setNumeroScooter(numeroScooter);
                scooter.setLocalisationScooter(localisationScooter);

                // Ajouter à la liste et à la base de données
                this.scooterList.add(scooter); // Ajouter à la liste
                System.out.println("Scooter ajouté : " + scooter);

                // Appeler le service pour ajouter le scooter à la base de données
                this.serviceScooter.ajouter(scooter);

                // Rafraîchir le ListView ou autre composant UI
                this.listViewScooter.refresh(); // Rafraîchir le ListView

                // Vider les champs de saisie
                this.numeroScooterField.clear();
                this.localisationScooterField.clear();

                // Afficher un message de succès
                this.showAlert(AlertType.INFORMATION, "Succès", "Scooter ajouté avec succès !");
            } else {
                // Afficher un message d'erreur si le numéro de scooter est déjà utilisé
                this.showAlert(AlertType.ERROR, "Erreur", "Le numéro de scooter est déjà utilisé. Veuillez en choisir un autre.");
            }
        } else {
            // Afficher un message d'erreur si les champs ne sont pas remplis
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
        }
    }

    // Méthode pour vérifier si le numéro du scooter est unique
    private boolean isNumeroScooterUnique(String numeroScooter) {
        // Vérifier si le numéro de scooter existe déjà dans la base de données ou la liste
        for (Scooter scooter : scooterList) {
            if (scooter.getNumeroScooter().equals(numeroScooter)) {
                return false; // Si le numéro existe déjà, retourner false
            }
        }
        return true; // Si le numéro est unique, retourner true
    }



    private void supprimerScooter(Scooter scooter) {
        if (scooter != null) {
            this.serviceScooter.supprimer(scooter.getIdScooter());
            this.loadScooters();
            this.showAlert(AlertType.INFORMATION, "Succès", "Scooter supprimé avec succès !");
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un scooter à supprimer !");
        }
    }

    public void filterScooterList() {
        String keyword = searchField.getText().toLowerCase(); // Récupère le texte saisi et le convertit en minuscule

        ObservableList<Scooter> filteredList = FXCollections.observableArrayList();

        for (Scooter scooter : scooterList) {
            // Recherche par numéro de scooter ou localisation
            if (scooter.getNumeroScooter().toLowerCase().contains(keyword) ||
                    scooter.getLocalisationScooter().toLowerCase().contains(keyword)) {
                filteredList.add(scooter);
            }
        }

        listViewScooter.setItems(filteredList); // Met à jour la ListView avec les résultats filtrés
    }

    private void modifierScooter(Scooter scooter) {
        if (scooter != null) {
            Dialog<Scooter> dialog = new Dialog<>();
            dialog.setTitle("Modifier le Scooter");
            dialog.setHeaderText("Modifiez les informations du scooter");
            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            TextField numeroScooterField = new TextField(scooter.getNumeroScooter());
            TextField localisationScooterField = new TextField(scooter.getLocalisationScooter());

            VBox dialogContent = new VBox(10.0, numeroScooterField, localisationScooterField);
            dialog.getDialogPane().setContent(dialogContent);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    scooter.setNumeroScooter(numeroScooterField.getText());
                    scooter.setLocalisationScooter(localisationScooterField.getText());
                    return scooter;
                } else {
                    return null;
                }
            });

            dialog.showAndWait().ifPresent(updatedScooter -> {
                this.serviceScooter.modifier(updatedScooter);
                this.loadScooters();
                this.showAlert(AlertType.INFORMATION, "Succès", "Scooter modifié avec succès !");
            });
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un scooter à modifier !");
        }
    }

    private void afficherDetails(Scooter scooter) {
        if (scooter != null) {
            String details = "ID: " + scooter.getIdScooter() + "\nNuméro: " + scooter.getNumeroScooter() + "\nLocalisation: " + scooter.getLocalisationScooter();
            this.showAlert(AlertType.INFORMATION, "Détails du Scooter", details);
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner un scooter à afficher !");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadScooters() {
        // Load scooters from the database and populate the list
        scooterList.clear();
        scooterList.addAll(serviceScooter.getAll());
    }

    // Create action buttons (modify and delete) for each item in the ListView
    private HBox createActionButtons(Scooter scooter) {
        Button modifyButton = new Button("Modifier");
        Button deleteButton = new Button("Supprimer");

        modifyButton.setOnAction(event -> {
            // Logic for modifying the scooter
            this.numeroScooterField.setText(scooter.getNumeroScooter());
            this.localisationScooterField.setText(scooter.getLocalisationScooter());
            this.scooterList.remove(scooter);
            this.serviceScooter.modifier(scooter);
        });

        deleteButton.setOnAction(event -> {
            // Logic for deleting the scooter
            this.scooterList.remove(scooter);
            this.serviceScooter.supprimer(scooter.getIdScooter());
        });

        return new HBox(5.0, modifyButton, deleteButton);
    }


}
