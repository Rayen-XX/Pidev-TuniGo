package com.esprit.gu.controller;//

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestionMenuController {
    public GestionMenuController() {
    }
    @FXML
    private Button notifButton; // Bouton de notification
    @FXML
    private ImageView notifIcon; // Icône de notification

    // Vous pouvez appeler cette méthode lorsque de nouvelles notifications arrivent
    public void updateNotificationIcon(boolean hasNewNotifications) {
        // Si de nouvelles notifications sont disponibles, on change l'icône
        if (hasNewNotifications) {
            // L'icône de notification active
            notifIcon.setImage(new Image(getClass().getResource("/Images/notification_active_icon.png").toString()));
        } else {
            // L'icône de notification inactive
            notifIcon.setImage(new Image(getClass().getResource("/Images/notification_icon.png").toString()));
        }
    }

    public void onNewNotificationReceived() {
        updateNotificationIcon(true); // Il y a de nouvelles notifications
    }

    // Méthode pour simuler la suppression ou la lecture de notifications
    public void onNotificationsRead() {
        updateNotificationIcon(false); // Plus de nouvelles notifications
    }
    private void changerScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlFile));
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException var6) {
            IOException e = var6;
            e.printStackTrace();
        }

    }

    public void goToBus(ActionEvent event) {
        this.changerScene(event, "/views/GestionMoyTrans/GestionBus.fxml");
    }

    public void goToTrain(ActionEvent event) {
        this.changerScene(event, "/views/GestionMoyTrans/GestionTrain.fxml");
    }

    public void goToMetro(ActionEvent event) {
        this.changerScene(event, "/views/GestionMoyTrans/GestionMetro.fxml");
    }

    public void goToScooter(ActionEvent event) {
        this.changerScene(event, "/views/GestionMoyTrans/GestionScooter.fxml");
    }

    public void goToTaxi(ActionEvent event) {
        this.changerScene(event, "/views/GestionMoyTrans/GestionTaxi.fxml");
    }

    public void goToReclamation(ActionEvent event) {
        this.changerScene(event, "/views/GestionMoyTrans/GestionReclamation.fxml");
    }

    public void goToReclamationuser(ActionEvent event) {
        this.changerScene(event, "/views/GestionMoyTrans/AjouterReclamation.fxml");
    }
    public void handleRetour(ActionEvent event) {
        this.changerScene(event, "/views/admin_dashboard_all.fxml");
    }
    /*public void goToNotification(ActionEvent event) {
        this.changerScene(event, "/views/notification.fxml");
    }*/
    public void goToNotification() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/notification.fxml"));
            VBox notificationDialog = loader.load(); // Use VBox here instead of AnchorPane


            // Créer un stage pour afficher la notification
            Stage notificationStage = new Stage();
            notificationStage.setTitle("Notifications");

            // Créer la scène avec l'interface du dialogue
            Scene scene = new Scene(notificationDialog);
            notificationStage.setScene(scene);

            // Empêcher la fermeture de l'application quand la fenêtre de notification est fermée
            notificationStage.setOnCloseRequest(event -> {
                event.consume();
                notificationStage.close();
            });

            // Afficher la fenêtre modale
            notificationStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
