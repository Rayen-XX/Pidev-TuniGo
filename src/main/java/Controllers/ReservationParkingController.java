package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Parking;
import models.Reservation;
import services.ParkingService;
import services.ReservationService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationParkingController {

    @FXML
    private Button parkingButton;
    @FXML
    private Button reservationButton;
    @FXML
    private ComboBox<Parking> parkingComboBox;
    @FXML
    private ComboBox<String> localisationComboBox;  // Ajouter la ComboBox pour les localisations
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button ListereservationButton;
    private ParkingService parkingService;
    private ReservationService reservationService;
    @FXML
    private ComboBox<String> timeComboBox;

    public ReservationParkingController() {
        parkingService = new ParkingService();
        reservationService = new ReservationService();
    }

    @FXML
    public void initialize() {
        remplirTimeComboBox();
        loadLocalisations();
        localisationComboBox.setOnAction(event -> loadParkingsByLocalisation(localisationComboBox.getValue()));
        loadParkingsByLocalisation(null);
        List<String> hours = new ArrayList<>();

    }

    private void remplirTimeComboBox() {
        ObservableList<String> heures = FXCollections.observableArrayList();

        // Ajouter des créneaux horaires toutes les 30 minutes
        for (int heure = 0; heure < 24; heure++) {
            for (int minute = 0; minute < 60; minute += 30) {
                String heureFormat = String.format("%02d:%02d", heure, minute);
                heures.add(heureFormat);
            }
        }

        timeComboBox.setItems(heures);
    }
    private void loadLocalisations() {
        try {
            List<Parking> parkings = parkingService.getAll();
            // Extraire les localisations uniques
            List<String> localisations = parkings.stream()
                    .map(Parking::getLocalisationParking)
                    .distinct()
                    .collect(Collectors.toList());
            // Remplir la ComboBox avec les localisations
            localisationComboBox.getItems().setAll(localisations);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Charger les parkings en fonction de la localisation choisie
    private void loadParkingsByLocalisation(String localisation) {
        try {
            List<Parking> parkings = parkingService.getAll();
            // Si une localisation est sélectionnée, filtrer les parkings par localisation
            if (localisation != null) {
                parkings = parkings.stream()
                        .filter(parking -> parking.getLocalisationParking().equals(localisation))
                        .collect(Collectors.toList());
            }
            // Filtrer les parkings ayant des places disponibles
            parkings.removeIf(parking -> parking.getPlace() <= 0);

            // Remplir la ComboBox avec les parkings disponibles
            parkingComboBox.getItems().setAll(parkings);

            // Utiliser un CellFactory pour afficher uniquement le nom du parking
            parkingComboBox.setCellFactory(param -> new javafx.scene.control.ListCell<Parking>() {
                @Override
                protected void updateItem(Parking parking, boolean empty) {
                    super.updateItem(parking, empty);
                    if (empty || parking == null) {
                        setText(null);
                    } else {
                        setText(parking.getNomParking()); // Afficher uniquement le nom du parking
                    }
                }
            });

            // Assurez-vous également que le texte affiché dans la ComboBox est uniquement le nom
            parkingComboBox.setButtonCell(new javafx.scene.control.ListCell<Parking>() {
                @Override
                protected void updateItem(Parking parking, boolean empty) {
                    super.updateItem(parking, empty);
                    if (empty || parking == null) {
                        setText(null);
                    } else {
                        setText(parking.getNomParking()); // Afficher uniquement le nom du parking
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    // Gérer la réservation du parking
    @FXML
    public void ReservationParking(ActionEvent event) {
        try {
            Parking selectedParking = parkingComboBox.getSelectionModel().getSelectedItem();
            LocalDate selectedDate = datePicker.getValue();
            String selectedTime = timeComboBox.getSelectionModel().getSelectedItem(); // Récupérer l'heure
            if (selectedParking != null && selectedDate != null && selectedTime != null) {
                // Convertir l'heure sélectionnée (ex: "14:30") en LocalDateTime
                int hour = Integer.parseInt(selectedTime.split(":")[0]);
                int minute = Integer.parseInt(selectedTime.split(":")[1]);

                LocalDateTime reservationDateTime = LocalDateTime.of(selectedDate, LocalTime.of(hour, minute));

                int userId = 1;
                Reservation reservation = new Reservation(reservationDateTime, userId, selectedParking.getIdParking());

                reservationService.create(reservation);

                selectedParking.setPlace(selectedParking.getPlace() - 1);
                parkingService.update(selectedParking);

                // Appliquer le format personnalisé
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy à HH:mm");
                String formattedDate = reservationDateTime.format(formatter);

                System.out.println("✅ Réservation confirmée pour le : " + formattedDate);
            } else {
                System.out.println("❌ Veuillez sélectionner un parking, une date et une heure");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleReservationParking()  {

        System.out.println("Reservation Parking button clicked");

    }
    // Gérer le clic sur le bouton Parking
    @FXML
    public void handleParking(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Parking.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
