package Controllers;

import entities.Reclamation;
import Services.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class GestionReclamation {

    @FXML
    private TableView<Reclamation> tableReclamation;

    @FXML
    private TableColumn<Reclamation, Integer> colId;

    @FXML
    private TableColumn<Reclamation, String> colType;

    @FXML
    private TableColumn<Reclamation, String> colDescription;

    @FXML
    private TableColumn<Reclamation, String> colStatut;

    @FXML
    private TableColumn<Reclamation, java.sql.Date> colDate;

    @FXML
    private TableColumn<Reclamation, String> colNom;

    @FXML
    private TableColumn<Reclamation, String> colPrenom;

    @FXML
    private TableColumn<Reclamation, Void> colAction;

    private ServiceReclamation serviceReclamation;
    private ObservableList<Reclamation> reclamationList;

    public GestionReclamation() {
        serviceReclamation = new ServiceReclamation();
        reclamationList = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        loadData();

        // Liaison des colonnes avec les propriétés de l'objet Reclamation.
        colId.setCellValueFactory(new PropertyValueFactory<>("idReclamation"));
        colType.setCellValueFactory(new PropertyValueFactory<>("typeReclamation"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descriptionReclamation"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statutReclamation"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateReclamation"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom_utilisateur"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom_utilisateur"));

        // Création de la colonne d'action avec des icônes.
        colAction.setCellFactory(new Callback<TableColumn<Reclamation, Void>, TableCell<Reclamation, Void>>() {
            @Override
            public TableCell<Reclamation, Void> call(TableColumn<Reclamation, Void> param) {
                return new TableCell<Reclamation, Void>() {
                    // Chargement des images depuis les ressources.
                    private final ImageView imgEdit = new ImageView(new Image(getClass().getResourceAsStream("/Images/edit_icon.png")));
                    private final ImageView imgDelete = new ImageView(new Image(getClass().getResourceAsStream("/Images/delete_icon.png")));
                    private final ImageView imgView = new ImageView(new Image(getClass().getResourceAsStream("/Images/detail_icon.png")));

                    {
                        // Configuration de la taille des icônes.
                        imgEdit.setFitWidth(16);
                        imgEdit.setFitHeight(16);
                        imgDelete.setFitWidth(16);
                        imgDelete.setFitHeight(16);
                        imgView.setFitWidth(16);
                        imgView.setFitHeight(16);

                        // Installation de tooltips.
                        Tooltip.install(imgEdit, new Tooltip("Modifier"));
                        Tooltip.install(imgDelete, new Tooltip("Supprimer"));
                        Tooltip.install(imgView, new Tooltip("Voir détails"));

                        // Gestionnaires d'événements pour chaque icône.
                        imgEdit.setOnMouseClicked(event -> {
                            Reclamation rec = getTableView().getItems().get(getIndex());
                            modifierReclamation(rec);
                        });
                        imgDelete.setOnMouseClicked(event -> {
                            Reclamation rec = getTableView().getItems().get(getIndex());
                            supprimerReclamation(rec);
                        });
                        imgView.setOnMouseClicked(event -> {
                            Reclamation rec = getTableView().getItems().get(getIndex());
                            voirDetailsReclamation(rec);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10, imgEdit, imgDelete, imgView);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    // Charge les réclamations depuis la base via le service et met à jour la TableView.
    private void loadData() {
        List<Reclamation> list = serviceReclamation.getAll();
        reclamationList.clear();
        reclamationList.addAll(list);
        tableReclamation.setItems(reclamationList);
    }

    // Méthode de suppression d'une réclamation.
    private void supprimerReclamation(Reclamation rec) {
        if (rec != null) {
            serviceReclamation.supprimer(rec.getIdReclamation());
            loadData();
            showAlert(AlertType.INFORMATION, "Succès", "Réclamation supprimée avec succès !");
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à supprimer !");
        }
    }
/*
    // Méthode de modification d'une réclamation avec formulaire de dialogue.
    private void modifierReclamation(Reclamation rec) {
        if (rec != null) {
            Dialog<Reclamation> dialog = new Dialog<>();
            dialog.setTitle("Modifier la Réclamation");
            dialog.setHeaderText("Modifiez les informations de la réclamation");

            // Définir les boutons du dialogue.
            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonType.OK.getButtonData());
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            // Création du formulaire dans une grille.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField typeField = new TextField();
            typeField.setText(rec.getTypeReclamation());
            TextField descriptionField = new TextField();
            descriptionField.setText(rec.getDescriptionReclamation());
            TextField statutField = new TextField();
            statutField.setText(rec.getStatutReclamation());
            TextField nomField = new TextField();
            nomField.setText(rec.getNom_utilisateur());
            TextField prenomField = new TextField();
            prenomField.setText(rec.getPrenom_utilisateur());
            DatePicker datePicker = new DatePicker();
            if (rec.getDateReclamation() != null) {
                java.sql.Date sqlDate = (Date) rec.getDateReclamation();
                LocalDate localDate = sqlDate.toLocalDate(); // Convertir java.sql.Date en LocalDate
                datePicker.setValue(localDate);
            }




            grid.add(new Label("Type:"), 0, 0);
            grid.add(typeField, 1, 0);
            grid.add(new Label("Description:"), 0, 1);
            grid.add(descriptionField, 1, 1);
            grid.add(new Label("Statut:"), 0, 2);
            grid.add(statutField, 1, 2);
            grid.add(new Label("Nom Utilisateur:"), 0, 3);
            grid.add(nomField, 1, 3);
            grid.add(new Label("Prénom Utilisateur:"), 0, 4);
            grid.add(prenomField, 1, 4);
            grid.add(new Label("Date:"), 0, 5);
            grid.add(datePicker, 1, 5);

            dialog.getDialogPane().setContent(grid);

            // Convertir le résultat lorsque l'utilisateur clique sur "Enregistrer".
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    rec.setTypeReclamation(typeField.getText());
                    rec.setDescriptionReclamation(descriptionField.getText());
                    rec.setStatutReclamation(statutField.getText());
                    rec.setNom_utilisateur(nomField.getText());
                    rec.setPrenom_utilisateur(prenomField.getText());
                    if (datePicker.getValue() != null) {
                        rec.setDateReclamation(Date.valueOf(datePicker.getValue()));
                    }
                    return rec;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedRec -> {
                serviceReclamation.modifier(updatedRec);
                loadData();
                showAlert(AlertType.INFORMATION, "Succès", "Réclamation modifiée avec succès !");
            });
        } else {
            showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à modifier !");
        }
    }*/
private void modifierReclamation(Reclamation rec) {
    if (rec != null) {
        Dialog<Reclamation> dialog = new Dialog<>();
        dialog.setTitle("Modifier la Réclamation");
        dialog.setHeaderText("Modifiez les informations de la réclamation");

        // Définir les boutons du dialogue.
        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonType.OK.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Création du formulaire dans une grille.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField typeField = new TextField();
        typeField.setText(rec.getTypeReclamation());
        TextField descriptionField = new TextField();
        descriptionField.setText(rec.getDescriptionReclamation());

        // ComboBox pour le statut
        ComboBox<String> statutComboBox = new ComboBox<>();
        statutComboBox.getItems().addAll("En attente", "Fait déjà");
        statutComboBox.setValue(rec.getStatutReclamation()); // Valeur initiale
        TextField nomField = new TextField();
        nomField.setText(rec.getNom_utilisateur());
        TextField prenomField = new TextField();
        prenomField.setText(rec.getPrenom_utilisateur());

        DatePicker datePicker = new DatePicker();
        if (rec.getDateReclamation() != null) {
            java.sql.Date sqlDate = (Date) rec.getDateReclamation();
            LocalDate localDate = sqlDate.toLocalDate(); // Convertir java.sql.Date en LocalDate
            datePicker.setValue(localDate);
        }

        grid.add(new Label("Type:"), 0, 0);
        grid.add(typeField, 1, 0);
        grid.add(new Label("Description:"), 0, 1);
        grid.add(descriptionField, 1, 1);
        grid.add(new Label("Statut:"), 0, 2);
        grid.add(statutComboBox, 1, 2);
        grid.add(new Label("Nom Utilisateur:"), 0, 3);
        grid.add(nomField, 1, 3);
        grid.add(new Label("Prénom Utilisateur:"), 0, 4);
        grid.add(prenomField, 1, 4);
        grid.add(new Label("Date:"), 0, 5);
        grid.add(datePicker, 1, 5);

        dialog.getDialogPane().setContent(grid);

        // Convertir le résultat lorsque l'utilisateur clique sur "Enregistrer".
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                // Validation du nom et prénom
                if (!isValidName(nomField.getText()) || !isValidName(prenomField.getText())) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Le nom et le prénom ne peuvent pas contenir de chiffres !");
                    return null;
                }

                // Validation du statut
                if (statutComboBox.getValue() == null) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner un statut !");
                    return null;
                }

                rec.setTypeReclamation(typeField.getText());
                rec.setDescriptionReclamation(descriptionField.getText());
                rec.setStatutReclamation(statutComboBox.getValue());
                rec.setNom_utilisateur(nomField.getText());
                rec.setPrenom_utilisateur(prenomField.getText());
                if (datePicker.getValue() != null) {
                    rec.setDateReclamation(Date.valueOf(datePicker.getValue()));
                }
                return rec;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(updatedRec -> {
            serviceReclamation.modifier(updatedRec);
            loadData();
            showAlert(AlertType.INFORMATION, "Succès", "Réclamation modifiée avec succès !");
        });
    } else {
        showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à modifier !");
    }
}

    // Méthode pour vérifier si le nom ou prénom contient des chiffres
    private boolean isValidName(String name) {
        return name != null && !name.matches(".*\\d.*"); // Vérifie si le nom ne contient pas de chiffres
    }


    // Méthode pour afficher les détails d'une réclamation dans une alerte.
    private void voirDetailsReclamation(Reclamation rec) {
        if (rec != null) {
            String details = "ID: " + rec.getIdReclamation() + "\n"
                    + "Type: " + rec.getTypeReclamation() + "\n"
                    + "Description: " + rec.getDescriptionReclamation() + "\n"
                    + "Statut: " + rec.getStatutReclamation() + "\n"
                    + "Date: " + rec.getDateReclamation() + "\n"
                    + "Nom: " + rec.getNom_utilisateur() + "\n"
                    + "Prénom: " + rec.getPrenom_utilisateur();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Détails de la Réclamation");
            alert.setHeaderText("Informations complètes");
            alert.setContentText(details);
            alert.showAndWait();
        }
    }

    // Méthode utilitaire pour afficher des alertes.
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
