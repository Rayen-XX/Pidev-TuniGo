package com.esprit.gu.controller;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

/*
import java.io.IOException;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import com.esprit.gu.entity.Reclamation;
import com.esprit.gu.service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;


public class GestionReclamation {
    @FXML
    private ListView<Reclamation> listViewReclamation;
    private ServiceReclamation serviceReclamation = new ServiceReclamation();
    private ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList();
    @FXML
    private Button routourButton;

    public GestionReclamation() throws Exception {
    }

    @FXML
    public void initialize() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));
        sortComboBox.setOnAction(event -> sortData());
        this.loadData();
        this.listViewReclamation.setItems(this.reclamationList);
        this.listViewReclamation.setCellFactory(new Callback<ListView<Reclamation>, ListCell<Reclamation>>() {
            public ListCell<Reclamation> call(ListView<Reclamation> param) {
                return new ListCell<Reclamation>() {
                    private final Button editButton = new Button("Modifier");
                    private final Button deleteButton = new Button("Supprimer");
                    private final Button viewButton = new Button("Voir Détails");
                    private final Text ReclamationInfo = new Text();
                    private final Region spacer = new Region();
                    private final Text typeText = new Text();
                    private final Text descriptionText = new Text();
                    private final Text statutText = new Text();
                    private final Text dateText = new Text();
                    private final HBox hbox;

                    {
                        this.hbox = new HBox(new Node[]{this.ReclamationInfo, this.spacer, this.editButton, this.deleteButton, this.viewButton});
                    }

                    protected void updateItem(Reclamation rec, boolean empty) {
                        super.updateItem(rec, empty);
                        if (!empty && rec != null) {
                            this.typeText.setText("Type: " + rec.getTypeReclamation());
                            this.descriptionText.setText("Description: " + rec.getDescriptionReclamation());
                            this.statutText.setText("Statut: " + rec.getStatutReclamation());
                            this.dateText.setText("Date: " + rec.getDateReclamation().toString());
                            HBox infoHBox = new HBox(10.0, new Node[]{this.typeText, this.descriptionText, this.statutText, this.dateText});
                            infoHBox.setSpacing(10.0);
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
                            iconHBox.setSpacing(20.0);
                            iconHBox.setAlignment(Pos.CENTER_RIGHT);
                            HBox rechbox = new HBox(20.0, new Node[]{infoHBox, iconHBox});
                            HBox.setHgrow(infoHBox, Priority.ALWAYS);
                            this.setGraphic(rechbox);
                            editIcon.setOnMouseClicked((event) -> {
                                GestionReclamation.this.modifierReclamation(rec);
                            });
                            deleteIcon.setOnMouseClicked((event) -> {
                                GestionReclamation.this.supprimerReclamation(rec);
                            });
                            viewIcon.setOnMouseClicked((event) -> {
                                GestionReclamation.this.voirDetailsReclamation(rec);
                            });
                        } else {
                            this.setGraphic((Node)null);
                        }

                    }
                };
            }
        });
    }

    private void loadData() {
        List<Reclamation> list = this.serviceReclamation.getAll();
        this.reclamationList.clear();
        this.reclamationList.addAll(list);
    }
    private void filterData(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            listViewReclamation.setItems(reclamations);
            return;
        }
        ObservableList<Reclamation> filteredList = FXCollections.observableArrayList(
                reclamations.stream()
                        .filter(r -> r.getTitre().toLowerCase().contains(keyword.toLowerCase()) ||
                                r.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                        .collect(Collectors.toList())
        );
        listViewReclamation.setItems(filteredList);
    }

    private void sortData() {
        String selectedSort = sortComboBox.getValue();
        if (selectedSort == null) return;

        Comparator<Reclamation> comparator = switch (selectedSort) {
            case "Date Croissante" -> Comparator.comparing(Reclamation::getDate);
            case "Date Décroissante" -> Comparator.comparing(Reclamation::getDate).reversed();
            default -> null;
        };

        if (comparator != null) {
            FXCollections.sort(reclamations, comparator);
            listViewReclamation.setItems(reclamations);
        }
    }


    private void supprimerReclamation(Reclamation rec) {
        if (rec != null) {
            this.serviceReclamation.supprimer(rec.getIdReclamation());
            this.loadData();
            this.showAlert(AlertType.INFORMATION, "Succès", "Réclamation supprimée avec succès !");
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à supprimer !");
        }

    }

    private void modifierReclamation(Reclamation rec) {
        Date sqlDate = new Date(System.currentTimeMillis());
        Instant instant = sqlDate.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant();
        if (rec != null) {
            Dialog<Reclamation> dialog = new Dialog();
            dialog.setTitle("Modifier la Réclamation");
            dialog.setHeaderText("Modifiez les informations de la réclamation");
            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonType.OK.getButtonData());
            dialog.getDialogPane().getButtonTypes().addAll(new ButtonType[]{saveButtonType, ButtonType.CANCEL});
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
            dialog.getDialogPane().setContent(new VBox(10.0, new Node[]{typeField, descriptionField, statutField, nomField, prenomField, datePicker}));
            dialog.setResultConverter((dialogButton) -> {
                if (dialogButton == saveButtonType) {
                    rec.setTypeReclamation(typeField.getText());
                    rec.setDescriptionReclamation(descriptionField.getText());
                    rec.setStatutReclamation(statutField.getText());
                    rec.setNom_utilisateur(nomField.getText());
                    rec.setPrenom_utilisateur(prenomField.getText());
                    if (datePicker.getValue() != null) {
                        rec.setDateReclamation(Date.valueOf((LocalDate)datePicker.getValue()));
                    }

                    return rec;
                } else {
                    return null;
                }
            });
            dialog.showAndWait().ifPresent((updatedRec) -> {
                this.serviceReclamation.modifier(updatedRec);
                this.loadData();
                this.showAlert(AlertType.INFORMATION, "Succès", "Réclamation modifiée avec succès !");
            });
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à modifier !");
        }

    }

    private void voirDetailsReclamation(Reclamation rec) {
        if (rec != null) {
            int var10000 = rec.getIdReclamation();
            String details = "ID: " + var10000 + "\nType: " + rec.getTypeReclamation() + "\nDescription: " + rec.getDescriptionReclamation() + "\nStatut: " + rec.getStatutReclamation() + "\nDate: " + String.valueOf(rec.getDateReclamation());
            this.showAlert(AlertType.INFORMATION, "Détails de la Réclamation", details);
        } else {
            this.showAlert(AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à afficher !");
        }

    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText((String)null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void routourButton() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/admin_dashboard_all.fxml"));
            Stage stage = (Stage) routourButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.esprit.gu.entity.Reclamation;
import com.esprit.gu.service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GestionReclamation {
    @FXML
    private ListView<Reclamation> listViewReclamation;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private Button retourButton;


    private final ServiceReclamation serviceReclamation = new ServiceReclamation();
    private final ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList();

    public GestionReclamation() throws Exception {
    }

    @FXML
    public void initialize() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterData(newValue));
        sortComboBox.setOnAction(event -> sortData());
        loadData();
        listViewReclamation.setItems(reclamationList);
        listViewReclamation.setCellFactory(param -> new ListCell<>() {
            //private final Button editButton = new Button("Modifier");
           // private final Button deleteButton = new Button("Supprimer");
           // private final Button viewButton = new Button("Détails");
            private final ImageView editIcon = new ImageView(new Image(getClass().getResource("/Images/update_icon.png").toExternalForm()));
            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResource("/Images/delete_icon.png").toExternalForm()));
            private final ImageView viewIcon = new ImageView(new Image(getClass().getResource("/Images/detail_icon.png").toExternalForm()));
            private final Text nomUtilisateurText = new Text();
            private final Text prenomUtilisateurText = new Text();
            private final Text typeText = new Text();
            private final Text descriptionText = new Text();
            private final Text statutText = new Text();
            private final Text dateText = new Text();
            private final HBox hbox = new HBox();
            private final Region spacer = new Region();
/*
            {
                editButton.setOnAction(event -> {
                    if (getItem() != null) modifierReclamation(getItem());
                });

                deleteButton.setOnAction(event -> {
                    if (getItem() != null) supprimerReclamation(getItem());
                });

                viewButton.setOnAction(event -> {
                    if (getItem() != null) voirDetailsReclamation(getItem());
                });
*/

            {
                editIcon.setFitWidth(24);
                editIcon.setFitHeight(24);
                deleteIcon.setFitWidth(24);
                deleteIcon.setFitHeight(24);
                viewIcon.setFitWidth(24);
                viewIcon.setFitHeight(24);

                editIcon.setOnMouseClicked(event -> {
                    if (getItem() != null) modifierReclamation(getItem());
                });

                deleteIcon.setOnMouseClicked(event -> {
                    if (getItem() != null) supprimerReclamation(getItem());
                });

                viewIcon.setOnMouseClicked(event -> {
                    if (getItem() != null) voirDetailsReclamation(getItem());
                });

                hbox.setSpacing(10);
                HBox.setHgrow(spacer, Priority.ALWAYS);
                hbox.getChildren().addAll(typeText, descriptionText,nomUtilisateurText,prenomUtilisateurText, statutText, dateText, spacer, editIcon, deleteIcon, viewIcon);
            }

            @Override
            protected void updateItem(Reclamation rec, boolean empty) {
                super.updateItem(rec, empty);
                if (empty || rec == null) {
                    setGraphic(null);
                } else {
                    typeText.setText("Type: " + rec.getTypeReclamation());
                    //descriptionText.setText("Description: " + rec.getDescriptionReclamation());
                    nomUtilisateurText.setText("Nom :" + rec.getNom_utilisateur());
                    prenomUtilisateurText.setText(("Prenom ")+rec.getPrenom_utilisateur());
                    statutText.setText("Statut: " + rec.getStatutReclamation());
                    dateText.setText("Date: " + rec.getDateReclamation());
                    setGraphic(hbox);
                }
            }
        });
    }

    private void loadData() {
        reclamationList.setAll(serviceReclamation.getAll());
    }

    private void filterData(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            listViewReclamation.setItems(reclamationList);
            return;
        }
        ObservableList<Reclamation> filteredList = FXCollections.observableArrayList(
                reclamationList.stream()
                        .filter(r -> r.getTypeReclamation().toLowerCase().contains(keyword.toLowerCase()) ||
                                r.getDescriptionReclamation().toLowerCase().contains(keyword.toLowerCase()))
                        .collect(Collectors.toList())
        );
        listViewReclamation.setItems(filteredList);
    }
/*
    private void sortData() {
        String selectedSort = sortComboBox.getValue();
        if (selectedSort == null) return;

        Comparator<Reclamation> comparator = null;

        if (selectedSort.equals("Date Croissante")) {
            comparator = Comparator.comparing(Reclamation::getDateReclamation);
        }
        if (selectedSort.equals("Date Décroissante")) {
            comparator = Comparator.comparing(Reclamation::getDateReclamation).reversed();
        }
        if (selectedSort.equals("En Attente")) {
            comparator = Comparator.comparing(r -> r.getStatutReclamation().equals("En Attente") ? 1 : 0);
        }
        if (selectedSort.equals("Résolue")) {
            comparator = Comparator.comparing(r -> r.getStatutReclamation().equals("Résolue") ? 1 : 0);
        }

        if (comparator != null) {
            FXCollections.sort(reclamationList, comparator);
            listViewReclamation.setItems(reclamationList);
        }
    }
*/

    private void sortData() {
        String selectedSort = sortComboBox.getValue();
        if (selectedSort == null) return;

        Comparator<Reclamation> comparator = null;
        ObservableList<Reclamation> filteredList = FXCollections.observableArrayList(reclamationList);

        if (selectedSort.equals("Date Croissante")) {
            comparator = Comparator.comparing(Reclamation::getDateReclamation);
        } else if (selectedSort.equals("Date Décroissante")) {
            comparator = Comparator.comparing(Reclamation::getDateReclamation).reversed();
        } else if (selectedSort.equals("En Attente")) {
            // On garde toutes les réclamations, juste en les triant par statut "En Attente"
            filteredList = filteredList.stream()
                    .filter(r -> r.getStatutReclamation().equals("En Attente"))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        } else if (selectedSort.equals("Résolue")) {
            // On filtre les réclamations avec le statut "Résolue"
            filteredList = filteredList.stream()
                    .filter(r -> r.getStatutReclamation().equals("Résolue"))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }

        if (comparator != null) {
            FXCollections.sort(filteredList, comparator);
        }

        listViewReclamation.setItems(filteredList);
    }


    private void supprimerReclamation(Reclamation rec) {
        if (rec != null) {
            serviceReclamation.supprimer(rec.getIdReclamation());
            loadData();
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation supprimée avec succès !");
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à supprimer !");
        }
    }
    private void modifierReclamation(Reclamation rec) {
        if (rec != null) {
            Dialog<Reclamation> dialog = new Dialog<>();
            dialog.setTitle("Modifier la Réclamation");
            dialog.setHeaderText("Modifiez les informations de la réclamation");

            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            //DatePicker datePicker = new DatePicker(rec.getDateReclamation().toLocalDate());

            TextField typeField = new TextField(rec.getTypeReclamation());
            TextField descriptionField = new TextField(rec.getDescriptionReclamation());

            // Création du ComboBox pour sélectionner le statut
            ComboBox<String> statutComboBox = new ComboBox<>();
            statutComboBox.getItems().addAll("En Attente", "Résolue");
            statutComboBox.setValue(rec.getStatutReclamation()); // Prend la valeur actuelle du statut

            dialog.getDialogPane().setContent(new VBox(10, typeField, descriptionField, statutComboBox/*, datePicker*/));

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    rec.setTypeReclamation(typeField.getText());
                    rec.setDescriptionReclamation(descriptionField.getText());
                    rec.setStatutReclamation(statutComboBox.getValue()); // Utilise la valeur du ComboBox pour le statut
                    //rec.setDateReclamation(Date.valueOf(datePicker.getValue()));
                    return rec;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedRec -> {
                serviceReclamation.modifier(updatedRec);
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation modifiée avec succès !");
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à modifier !");
        }
    }

/*
    private void modifierReclamation(Reclamation rec) {
        if (rec != null) {
            Dialog<Reclamation> dialog = new Dialog<>();
            dialog.setTitle("Modifier la Réclamation");
            dialog.setHeaderText("Modifiez les informations de la réclamation");
            ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
            DatePicker datePicker = new DatePicker();

            TextField typeField = new TextField(rec.getTypeReclamation());
            TextField descriptionField = new TextField(rec.getDescriptionReclamation());
            TextField statutField = new TextField(rec.getStatutReclamation());
            if (datePicker.getValue() != null) {
                rec.setDateReclamation(Date.valueOf((LocalDate)datePicker.getValue()));
            }
            //DatePicker datePicker = new DatePicker(rec.getDateReclamation().toLocalDate());

            dialog.getDialogPane().setContent(new VBox(10, typeField, descriptionField, statutField, datePicker));

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveButtonType) {
                    rec.setTypeReclamation(typeField.getText());
                    rec.setDescriptionReclamation(descriptionField.getText());
                    rec.setStatutReclamation(statutField.getText());
                    rec.setDateReclamation(Date.valueOf(datePicker.getValue()));
                    return rec;
                }
                return null;
            });

            dialog.showAndWait().ifPresent(updatedRec -> {
                serviceReclamation.modifier(updatedRec);
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation modifiée avec succès !");
            });
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une réclamation à modifier !");
        }
    }*/

    private void voirDetailsReclamation(Reclamation rec) {
        if (rec != null) {
            String details = "ID : " + rec.getIdReclamation() + "\n"
                    + "Nom utilistaur : " +rec.getNom_utilisateur()+"\n"
                    + "Prenom utilistaur : " +rec.getPrenom_utilisateur()+"\n"
                    + "Type : " + rec.getTypeReclamation() + "\n"
                    + "Description : " + rec.getDescriptionReclamation() + "\n"
                    + "Statut : " + rec.getStatutReclamation() + "\n"
                    + "Date : " + rec.getDateReclamation();

            showAlert(Alert.AlertType.INFORMATION, "Détails de la Réclamation", details);
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);

        // Ajouter des labels pour une meilleure présentation
        Label label = new Label(message);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333;");

        // Ajouter le label au dialogue d'alerte
        alert.getDialogPane().setContent(label);

        // Afficher l'alerte
        alert.showAndWait();
    }


    @FXML
    private void retourButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/admin_dashboard_all.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
