package com.esprit.gu.util;

/**
 * This class provides a bridge to access Google API classes from the modular application.
 * It helps overcome JPMS restrictions when accessing classes from unnamed modules.
 */
public class GoogleApiBridge {
    
    /**
     * Ensures the Google API classes are loaded and accessible.
     * Call this method before using Google API.
     */
    public static void ensureGoogleApiAccess() {
        try {
            // Force loading of key Google API classes
            Class.forName("com.google.api.client.auth.oauth2.Credential");
            Class.forName("com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow");
            Class.forName("com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse");
            Class.forName("com.google.api.client.googleapis.javanet.GoogleNetHttpTransport");
            Class.forName("com.google.api.client.json.gson.GsonFactory");
            Class.forName("com.google.api.services.people.v1.PeopleService");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load Google API classes", e);
        }
    }
} 