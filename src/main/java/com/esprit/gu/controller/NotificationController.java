package com.esprit.gu.controller;

import com.esprit.gu.firebaseconfig.NotificationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NotificationController {

    @FXML
    private ListView<String> notificationsListView;
    // ListView pour afficher les notifications
    @FXML
    private Label notificationLabel;


    @FXML
    private Button closeButton;

   @FXML
    private void initialize() {
        loadNotifications();
    }
    @FXML
    private void closeNotification() {
        Stage stage = (Stage) notificationLabel.getScene().getWindow();
        stage.close();
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
    // Charger les notifications dans la ListView
    private void loadNotifications() {
        NotificationService.getNotifications(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<String> notifications = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Récupérer les données de notification
                    String title = snapshot.child("title").getValue(String.class);
                    String userId = snapshot.child("userId").getValue(String.class);

                    if (title != null && userId != null) {
                        String userName = getUserNameById(userId);

                        String notificationText = "Nouvelle réclamation soumise par " + userName;
                        notifications.add(notificationText);
                    }
                }
                notificationsListView.getItems().setAll(notifications);  // Afficher dans la ListView
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("❌ Erreur lors de la récupération des notifications: " + databaseError.getMessage());
            }
        });
    }

    private String getUserNameById(String userId) {
        return " " + userId;  // À personnaliser selon votre logique
    }

    public void setNotificationText(String text) {
        notificationLabel.setText(text);
    }

    @FXML
    private void closeNotification(ActionEvent event) {
        this.changerScene(event, "/views/admin_dashboard_all.fxml");}
}

