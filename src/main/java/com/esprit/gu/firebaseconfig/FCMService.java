/*package com.esprit.gu.firebaseconfig;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

public class FCMService {

    public static void sendNotification(String token, String title, String body) {
        try {
            // Vérifie que Firebase est initialisé
            FirebaseConfig.initFirebase();

            Notification notification = Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build();

            Message message = Message.builder()
                    .setToken(token)
                    .setNotification(notification)
                    .build();

            // Envoyer la notification via Firebase
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notification envoyée avec succès : " + response);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'envoi de la notification : " + e.getMessage());
            e.printStackTrace();
        }
    }
}*/