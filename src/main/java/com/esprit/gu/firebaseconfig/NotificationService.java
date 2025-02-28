/*package com.esprit.gu.firebaseconfig;

import com.google.firebase.database.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NotificationService {

    public static void envoyerNotification(String userId, String messageContent) {
        try {
            // Vérifier si Firebase n'est pas déjà initialisé
            if (FirebaseApp.getApps().isEmpty()) {
                // Initialisation de Firebase avec le fichier de configuration
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-service.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options); // Initialiser Firebase
                System.out.println("Firebase est bien configuré !");
            }

            // Préparer la notification
            Notification notification = Notification.builder()
                    .setTitle("Nouvelle Réclamation")
                    .setBody(messageContent)  // Message contenu de la notification
                    .build();

            // Créer un message avec un token d'utilisateur ou un topic
            Message message = Message.builder()
                    .setTopic("admin_notifications")  // Utilisation d'un topic pour envoyer à tous les admins
                    .setNotification(notification)
                    .putData("userId", userId) // Ajouter des données personnalisées comme l'ID de l'utilisateur
                    .build();

            // Envoyer la notification
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notification envoyée avec succès : " + response);

            // Stocker la notification dans Realtime Database après envoi
            storeNotificationInRealtimeDatabase(userId, messageContent, response);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur d'initialisation de Firebase ou d'envoi de notification.");
            System.err.println("Cause : " + e.getCause());
            System.err.println("Message : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de l'envoi de la notification.");
            System.err.println("Cause : " + e.getCause());
            System.err.println("Message : " + e.getMessage());
            System.err.println("Stack Trace : ");
            for (StackTraceElement element : e.getStackTrace()) {
                System.err.println("\t at " + element);
            }
        }

    }

    // Méthode pour stocker une notification dans Realtime Database
    private static void storeNotificationInRealtimeDatabase(String userId, String messageContent, String fcmMessageId) {
        // Initialiser Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("notifications");

        // Créer les données de notification
        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("title", "Nouvelle Réclamation");
        notificationData.put("body", messageContent);
        notificationData.put("userId", userId);
        notificationData.put("timestamp", System.currentTimeMillis());  // Utilise le timestamp actuel
        notificationData.put("status", "envoyée");
        notificationData.put("fcmMessageId", fcmMessageId);  // ID du message FCM

        // Ajouter la notification à la collection "notifications" dans la base de données
        DatabaseReference newNotificationRef = notificationsRef.push();

        // Utiliser get() pour attendre la fin de l'opération synchroniquement
        try {
            newNotificationRef.setValueAsync(notificationData).get();  // Bloque jusqu'à la fin de l'opération
            System.out.println("✅ Notification enregistrée dans Realtime Database avec succès !");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de l'enregistrement de la notification dans Realtime Database.");
        }
    }


    // Récupérer les notifications depuis Firebase
    public static void getNotifications(ValueEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("notifications");

        // Ajouter un listener pour écouter les changements
        notificationsRef.addValueEventListener(listener);
    }


    // Classe interne représentant les données d'une notification
    public static class NotificationData {
        private String title;
        private String body;
        private String userId;
        private String fcmMessageId;

        public NotificationData() {} // Constructeur par défaut nécessaire pour Firebase

        public NotificationData(String title, String body, String userId, String fcmMessageId) {
            this.title = title;
            this.body = body;
            this.userId = userId;
            this.fcmMessageId = fcmMessageId;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }

        public String getUserId() {
            return userId;
        }

        public String getFcmMessageId() {
            return fcmMessageId;
        }

        @Override
        public String toString() {
            return title + ": " + body;
        }
    }
}
*/

package com.esprit.gu.firebaseconfig;

import com.esprit.gu.util.Session;
import com.google.firebase.database.*;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class NotificationService {

    // Méthode pour envoyer une notification
    public static void envoyerNotification(String userId, String messageContent) {
        try {
            // Vérifier si Firebase n'est pas déjà initialisé
            if (FirebaseApp.getApps().isEmpty()) {
                // Initialisation de Firebase avec le fichier de configuration
                FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-service.json");

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(options); // Initialiser Firebase
                System.out.println("Firebase est bien configuré !");
            }

            // Préparer la notification
            Notification notification = Notification.builder()
                    .setTitle("Nouvelle Réclamation")
                    .setBody(messageContent)  // Message contenu de la notification
                    .build();

            // Créer un message avec un token d'utilisateur ou un topic
            Message message = Message.builder()
                    .setTopic("admin_notifications")  // Utilisation d'un topic pour envoyer à tous les admins
                    .setNotification(notification)
                    .putData("nom utilisateur", Session.getCurrentUser().getNomUtilisateur())
                    .putData("prenom utilisateur", Session.getCurrentUser().getPrenomUtilisateur())
                    .build();

            // Envoyer la notification
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("✅ Notification envoyée avec succès : " + response);

            // Stocker la notification dans Realtime Database après envoi
            storeNotificationInRealtimeDatabase(userId, messageContent, response);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur d'initialisation de Firebase ou d'envoi de notification.");
            System.err.println("Cause : " + e.getCause());
            System.err.println("Message : " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de l'envoi de la notification.");
            System.err.println("Cause : " + e.getCause());
            System.err.println("Message : " + e.getMessage());
            System.err.println("Stack Trace : ");
            for (StackTraceElement element : e.getStackTrace()) {
                System.err.println("\t at " + element);
            }
        }

    }

    // Méthode pour stocker une notification dans Realtime Database
    private static void storeNotificationInRealtimeDatabase(String userId, String messageContent, String fcmMessageId) {
        // Initialiser Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("notifications");

        // Créer les données de notification
        Map<String, Object> notificationData = new HashMap<>();
        notificationData.put("title", "Nouvelle Réclamation");
        notificationData.put("body", messageContent);
        notificationData.put("userId", Session.getCurrentUser().getNomUtilisateur());
        notificationData.put("userId", Session.getCurrentUser().getPrenomUtilisateur());

        notificationData.put("timestamp", System.currentTimeMillis());  // Utilise le timestamp actuel
        notificationData.put("status", "envoyée");
        notificationData.put("fcmMessageId", fcmMessageId);

        // Ajouter la notification à la collection "notifications" dans la base de données
        DatabaseReference newNotificationRef = notificationsRef.push();

        // Utiliser get() pour attendre la fin de l'opération synchroniquement
        try {
            newNotificationRef.setValueAsync(notificationData).get();  // Bloque jusqu'à la fin de l'opération
            System.out.println("✅ Notification enregistrée dans Realtime Database avec succès !");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.err.println("❌ Erreur lors de l'enregistrement de la notification dans Realtime Database.");
        }
    }

    // Récupérer les notifications depuis Firebase Realtime Database
    public static void getNotifications(ValueEventListener listener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference notificationsRef = database.getReference("notifications");

        // Ajouter un listener pour écouter les changements
        notificationsRef.addValueEventListener(listener);
    }

}