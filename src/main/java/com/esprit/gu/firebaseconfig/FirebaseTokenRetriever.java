/*package com.esprit.gu.firebaseconfig;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;

public class FirebaseTokenRetriever {

    /**
     * Récupère le token FCM d'un utilisateur à partir de Firestore.
     *
     * @param userId L'ID de l'utilisateur.
     * @return Le token FCM de l'utilisateur, ou null si l'utilisateur n'existe pas ou n'a pas de token.
     *//*
    public static String getUserFcmToken(String userId) {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document(userId);

        try {
            // Récupérer les données de l'utilisateur de manière asynchrone
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get(); // Attend la fin de la requête

            if (document.exists()) {
                String fcmToken = document.getString("fcmToken");
                if (fcmToken != null && !fcmToken.isEmpty()) {
                    return fcmToken; // Retourne le token FCM
                } else {
                    System.out.println("⚠️ L'utilisateur " + userId + " n'a pas de token FCM.");
                }
            } else {
                System.out.println("❌ Aucun utilisateur trouvé avec l'ID : " + userId);
            }
        } catch (InterruptedException e) {
            System.err.println("❌ La requête a été interrompue : " + e.getMessage());
            Thread.currentThread().interrupt(); // Restaurer l'état d'interruption
        } catch (ExecutionException e) {
            System.err.println("❌ Erreur lors de la récupération du document : " + e.getMessage());
        } catch (FirestoreException e) {
            System.err.println("❌ Erreur Firestore : " + e.getMessage());
        }

        return null; // Retourne null si le token n'est pas trouvé
    }
}*/