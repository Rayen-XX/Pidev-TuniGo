package com.esprit.gu.firebaseconfig;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileInputStream;

public class FirebaseConfig {
    public static void initFirebase() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase-service.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://tunigoreclamations-default-rtdb.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("🔥 Firebase connecté !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }
}
