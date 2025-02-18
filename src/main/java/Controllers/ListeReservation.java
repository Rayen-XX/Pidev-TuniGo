package Controllers;

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
import models.Reservation;
import services.ParkingService;
import services.ReservationService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ListeReservation {

    @FXML
    private ListView<Reservation> reservationListView;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Button parkingButton;

    @FXML
    private Button reservationButton;


    private ObservableList<Reservation> reservationList;
    private ReservationService reservationService;

    public ListeReservation() {
        reservationService = new ReservationService();
    }

    @FXML
    public void initialize() {
        loadReservationList();
        reservationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Lorsque l'utilisateur sélectionne une réservation, on active les boutons
                updateButton.setDisable(false);
                deleteButton.setDisable(false);
            }
        });
    }

    private void loadReservationList() {
        try {
            ParkingService parkingService=new ParkingService();
            List<Reservation> reservations = reservationService.getAll();
            reservationList = FXCollections.observableArrayList(reservations);
            reservationListView.setCellFactory(lv -> new ListCell<Reservation>() {
                @Override
                protected void updateItem(Reservation reservation, boolean empty) {
                    super.updateItem(reservation, empty);
                    if (empty || reservation == null) {
                        setText(null);
                    } else {

                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy à HH:mm");
                            setText(reservationService.getUser(reservation.getIdUtilisateur()) + " - " +" Nom : "+parkingService.getById(reservation.getIdParking()).getNomParking()+" - "+ reservation.getDateReservation().format(formatter));
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            //comm

            reservationListView.setItems(reservationList); // Assigner la liste des réservations à la ListView
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDelete() {
        try {
            Reservation selectedReservation = reservationListView.getSelectionModel().getSelectedItem();
            if (selectedReservation != null) {
                reservationService.delete(selectedReservation); // Supprimer la réservation depuis la base
                loadReservationList(); // Recharger la liste des réservations après suppression
                updateButton.setDisable(true);
                deleteButton.setDisable(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleUpdateReservation() {
        System.out.println("Bouton Modifier Date cliqué !");
        Reservation selectedReservation = reservationListView.getSelectionModel().getSelectedItem();

        if (selectedReservation != null) {
            // Afficher un dialog pour saisir la nouvelle date
            TextInputDialog dialog = new TextInputDialog(selectedReservation.getDateReservation().toString()); // Afficher la date actuelle par défaut
            dialog.setTitle("Modifier Date de Réservation");
            dialog.setHeaderText(null);
            dialog.setContentText("Nouvelle date de réservation (format: yyyy-MM-dd):");

            // Ouvrir le dialogue et traiter la saisie
            dialog.showAndWait().ifPresent(dateStr -> {
                try {
                    // Valider et convertir la nouvelle date
                    LocalDateTime newDate = LocalDateTime.parse(dateStr);

                    // Mettre à jour la réservation avec la nouvelle date
                    selectedReservation.setDateReservation(newDate);
                    selectedReservation.setIdTaxi(null);
                    selectedReservation.setIdScooter(null);
                    selectedReservation.setIdTrajetMetro(null);
                    selectedReservation.setIdTrajetTrain(null);
                    selectedReservation.setIdTrajetBus(null);

                    // Appeler la méthode de mise à jour du service pour enregistrer la modification dans la base de données
                    reservationService.update(selectedReservation);

                    // Rafraîchir la liste des réservations après la mise à jour
                    loadReservationList();
                } catch (DateTimeParseException e) {
                    errorLabel.setText("Format de date invalide. Utilisez le format : yyyy-MM-dd.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
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
    public void handleParking(ActionEvent event) throws IOException {
        Button sourceButton = (Button) event.getSource();
        if (event.getSource()==parkingButton) {
            // Charger le fichier FXML de l'interface panier
            Parent root = FXMLLoader.load(getClass().getResource("/Parking.fxml"));

            // Récupérer la scène actuelle et changer son contenu
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        System.out.println("Parking button clicked");
    }
}
