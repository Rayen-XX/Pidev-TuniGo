package com.esprit.gu.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.esprit.gu.entity.Parking;
import com.esprit.gu.service.ParkingService;

import java.io.IOException;
import java.util.List;

public class ParkingController {

    @FXML
    private ListView<Parking> parkingListView;
    @FXML
    private TextField nomParkingField;
    @FXML
    private TextField localisationField;
    @FXML
    private TextField nbPlaceField;
    @FXML
    private Button saveButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button reservationButton;
    @FXML
    private Button ListereservationButton;

    private ParkingService parkingService;
    private ObservableList<Parking> parkingList;

    public ParkingController() {
        try {
            parkingService = new ParkingService();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void initialize() {
        loadParkingList();
        parkingListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nomParkingField.setText(newValue.getNomParking());
                localisationField.setText(newValue.getLocalisationParking());
                nbPlaceField.setText(String.valueOf(newValue.getPlace()));
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });
    }

    private void loadParkingList() {
        try {
            List<Parking> parkings = parkingService.getAll();
            parkingList = FXCollections.observableArrayList(parkings);
            parkingListView.setCellFactory(lv -> new ListCell<Parking>() {
                @Override
                protected void updateItem(Parking parking, boolean empty) {
                    super.updateItem(parking, empty);
                    if (empty || parking == null) {
                        setText(null);
                    } else {
                        setText("Nom: "+parking.getNomParking() + " - " + "Localisation: "+parking.getLocalisationParking() + " - Nb Places: " + parking.getPlace());
                    }
                }
            });

            // Assigner la liste des parkings à la ListView
            parkingListView.setItems(parkingList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleSave() {
        if (isInputValid()) {
            try {
                String nomParking = nomParkingField.getText();
                String localisation = localisationField.getText();
                int nbPlace = validateNbPlace(nbPlaceField.getText()); // Validation de la saisie

                if (nbPlace != -1) { // Si la saisie est valide
                    Parking parking = new Parking(nomParking, localisation, nbPlace);
                    parkingService.create(parking);
                    loadParkingList();
                    clearFields();
                } else {
                    errorLabel.setText("Veuillez entrer un nombre valide pour le nombre de places.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Tous les champs doivent être remplis.");
        }
    }

    @FXML
    public void handleUpdate() {
        if (isInputValid()) {
            try {
                Parking selectedParking = parkingListView.getSelectionModel().getSelectedItem();
                if (selectedParking != null) {
                    int nbPlace = validateNbPlace(nbPlaceField.getText()); // Validation de la saisie

                    if (nbPlace != -1) {
                        selectedParking.setNomParking(nomParkingField.getText());
                        selectedParking.setLocalisationParking(localisationField.getText());
                        selectedParking.setPlace(nbPlace);
                        parkingService.update(selectedParking);
                        loadParkingList();
                        clearFields();
                        updateButton.setDisable(true);
                        deleteButton.setDisable(true);
                    } else {
                        errorLabel.setText("Veuillez entrer un nombre valide pour le nombre de places.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Tous les champs doivent être remplis.");
        }
    }

    @FXML
    public void handleDelete() {
        try {
            Parking selectedParking = parkingListView.getSelectionModel().getSelectedItem();
            if (selectedParking != null) {
                parkingService.delete(selectedParking);
                loadParkingList();
                clearFields();
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nomParkingField.clear();
        localisationField.clear();
        nbPlaceField.clear();
        errorLabel.setText("");
    }

    private int validateNbPlace(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1; //
        }
    }

    private boolean isInputValid() {
        return !nomParkingField.getText().isEmpty() && !localisationField.getText().isEmpty() && !nbPlaceField.getText().isEmpty();
    }

    @FXML
    public void handleReservationParking(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();
        if (event.getSource()==reservationButton) {
            // Charger le fichier FXML de l'interface panier
            Parent root = FXMLLoader.load(getClass().getResource("/ReservationParking.fxml"));

            // Récupérer la scène actuelle et changer son contenu
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        System.out.println("Reservation Parking button clicked");

    }
    @FXML
    public void handleParking() {
        System.out.println("Parking button clicked");
    }

    public void handleListeReservationParking(ActionEvent event) throws IOException{
        Button sourceButton = (Button) event.getSource();
        if (event.getSource()==ListereservationButton) {
            // Charger le fichier FXML de l'interface panier
            Parent root = FXMLLoader.load(getClass().getResource("/ListeReservation.fxml"));

            // Récupérer la scène actuelle et changer son contenu
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}