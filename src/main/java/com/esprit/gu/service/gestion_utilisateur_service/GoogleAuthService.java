package com.esprit.gu.service.gestion_utilisateur_service;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.util.GoogleApiBridge;
import com.esprit.gu.util.Session;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.Person;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoogleAuthService {
    private static final String CONFIG_FILE = "google-config.properties";
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;
    private UtilisateurService utilisateurService;
    
    // Google API specific objects
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private HttpTransport httpTransport;
    private List<String> SCOPES = Arrays.asList(
            PeopleServiceScopes.USERINFO_PROFILE,
            PeopleServiceScopes.USERINFO_EMAIL);
    
    public GoogleAuthService() {
        try {
            // Ensure Google API classes are accessible
            GoogleApiBridge.ensureGoogleApiAccess();
            
            // Initialize HTTP transport
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            
            // Load configuration
            loadProperties();
            
            // Initialize user service
            utilisateurService = new UtilisateurService();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize GoogleAuthService", e);
        }
    }
    
    private void loadProperties() {
        Properties prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + CONFIG_FILE);
                throw new RuntimeException(CONFIG_FILE + " file not found in classpath");
            }
            
            prop.load(input);
            clientId = prop.getProperty("google.client.id");
            clientSecret = prop.getProperty("google.client.secret");
            redirectUri = prop.getProperty("google.redirect.uri");
            scope = prop.getProperty("google.auth.scope");
            
            if (clientId == null || clientSecret == null || redirectUri == null) {
                throw new RuntimeException("Required Google OAuth properties are missing");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load Google OAuth properties", e);
        }
    }
    
    private boolean isWebViewAvailable() {
        try {
            Class.forName("javafx.scene.web.WebView");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    public CompletableFuture<Utilisateur> startGoogleLogin(Stage primaryStage) {
        CompletableFuture<Utilisateur> future = new CompletableFuture<>();
        
        if (!isWebViewAvailable()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("WebView Not Available");
                alert.setContentText("The WebView component is not available in this JavaFX configuration.");
                alert.showAndWait();
                future.completeExceptionally(new RuntimeException("WebView not available"));
            });
            return future;
        }
        
        try {
            // Create the authorization flow
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientId, clientSecret, SCOPES)
                    .setAccessType("offline")
                    .build();
            
            // Generate the authorization URL
            GoogleAuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl()
                    .setRedirectUri(redirectUri)
                    .setApprovalPrompt("force"); // Force approval to get refresh token
            
            // Create a WebView to display the Google login page
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            
            // Create a new stage for the WebView
            Stage webViewStage = new Stage();
            webViewStage.setTitle("Google Sign In");
            webViewStage.setScene(new javafx.scene.Scene(webView, 800, 600));
            
            // Handle URL changes to detect the authorization code
            webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("WebView navigation to: " + newValue);
                
                if (newValue != null && newValue.startsWith(redirectUri)) {
                    System.out.println("Detected redirect URI, extracting code");
                    
                    try {
                        // Extract the authorization code from the URL
                        String code = null;
                        
                        // Try to get code from query parameters (normal flow)
                        if (newValue.contains("?code=")) {
                            code = extractCodeFromUrl(newValue);
                            System.out.println("Extracted code from query parameters: " + (code != null ? "success" : "failure"));
                        }
                        // Try to get code from hash fragment (alternative flow)
                        else if (newValue.contains("#code=")) {
                            String fragment = newValue.substring(newValue.indexOf('#') + 1);
                            code = extractParameterFromFragment(fragment, "code");
                            System.out.println("Extracted code from fragment: " + (code != null ? "success" : "failure"));
                        }
                        
                        if (code != null) {
                            // Close the WebView stage
                            webViewStage.close();
                            
                            final String authCode = code; // Create a final copy for use in the task
                            
                            // Exchange the authorization code for tokens
                            Task<Utilisateur> exchangeTask = new Task<>() {
                                @Override
                                protected Utilisateur call() throws Exception {
                                    try {
                                        System.out.println("Exchanging authorization code for tokens");
                                        
                                        // Exchange the authorization code for tokens - use the exact same redirect URI
                                        GoogleTokenResponse tokenResponse = flow.newTokenRequest(authCode)
                                                .setRedirectUri(redirectUri)
                                                .execute();
                                        
                                        System.out.println("Token exchange successful");
                                        
                                        // Use the access token to get user info
                                        Credential credential = flow.createAndStoreCredential(tokenResponse, null);
                                        PeopleService peopleService = new PeopleService.Builder(
                                                httpTransport, JSON_FACTORY, credential)
                                                .setApplicationName("TuniGo")
                                                .build();
                                        
                                        // Get the user's profile information
                                        Person profile = peopleService.people().get("people/me")
                                                .setPersonFields("names,emailAddresses,photos")
                                                .execute();
                                        
                                        // Process the user information
                                        return processUserInfo(profile);
                                    } catch (Exception e) {
                                        System.out.println("Error exchanging code for tokens: " + e.getMessage());
                                        e.printStackTrace();
                                        throw e;
                                    }
                                }
                            };
                            
                            exchangeTask.setOnSucceeded(event -> {
                                Utilisateur user = exchangeTask.getValue();
                                future.complete(user);
                            });
                            
                            exchangeTask.setOnFailed(event -> {
                                future.completeExceptionally(exchangeTask.getException());
                            });
                            
                            new Thread(exchangeTask).start();
                        } else if (newValue.contains("error=")) {
                            // Handle authentication error
                            String error = extractParameter(newValue, "error");
                            System.out.println("Auth error in URL: " + error);
                            webViewStage.close();
                            future.completeExceptionally(new RuntimeException("Authentication error: " + error));
                        } else {
                            System.out.println("Could not extract code from URL: " + newValue);
                        }
                    } catch (Exception e) {
                        System.out.println("Exception handling redirect: " + e.getMessage());
                        e.printStackTrace();
                        webViewStage.close();
                        future.completeExceptionally(e);
                    }
                }
            });
            
            // Load the Google authorization URL
            String authUrl = authorizationUrl.build();
            System.out.println("Loading authorization URL: " + authUrl);
            webEngine.load(authUrl);
            
            // Show the WebView stage
            webViewStage.show();
            
        } catch (Exception e) {
            System.out.println("Exception initiating auth flow: " + e.getMessage());
            e.printStackTrace();
            future.completeExceptionally(e);
        }
        
        return future;
    }
    
    // Extract code from a standard URL with query parameters
    private String extractCodeFromUrl(String url) {
        try {
            // Parse the URL to get the query part
            java.net.URI uri = new java.net.URI(url);
            String query = uri.getQuery();
            
            if (query != null) {
                // Split the query string into key-value pairs
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    // Split each pair into key and value
                    String[] keyValue = pair.split("=");
                    if (keyValue.length > 1 && "code".equals(keyValue[0])) {
                        // URL decode the authorization code
                        return java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error extracting code from URL: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private String extractParameter(String url, String paramName) {
        try {
            if (url.contains("?")) {
                String query = url.split("\\?")[1];
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length > 1 && keyValue[0].equals(paramName)) {
                        return java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error extracting parameter from URL: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private String extractParameterFromFragment(String fragment, String paramName) {
        try {
            String[] pairs = fragment.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length > 1 && keyValue[0].equals(paramName)) {
                    return java.net.URLDecoder.decode(keyValue[1], "UTF-8");
                }
            }
        } catch (Exception e) {
            System.out.println("Error extracting parameter from fragment: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    private Utilisateur processUserInfo(Person profile) throws Exception {
        try {
            // Extract user information from the Google profile
            String email = profile.getEmailAddresses() != null && !profile.getEmailAddresses().isEmpty() 
                    ? profile.getEmailAddresses().get(0).getValue() : null;
            
            String fullName = profile.getNames() != null && !profile.getNames().isEmpty() 
                    ? profile.getNames().get(0).getDisplayName() : null;
            
            String profilePictureUrl = profile.getPhotos() != null && !profile.getPhotos().isEmpty() 
                    ? profile.getPhotos().get(0).getUrl() : null;
            
            if (email == null || fullName == null) {
                throw new Exception("Could not retrieve required user information from Google");
            }
            
            System.out.println("Processing Google user: " + fullName + " with email: " + email);
            
            // Check if user already exists in our database
            Utilisateur existingUser = utilisateurService.getUtilisateurByEmail(email);
            
            if (existingUser != null) {
                // User exists, update their session
                System.out.println("User already exists in database, returning existing user");
                Session.setCurrentUser(existingUser);
                return existingUser;
            } else {
                // User doesn't exist, create a new account
                String[] nameParts = splitName(fullName);
                String firstName = nameParts[0];
                String lastName = nameParts.length > 1 ? nameParts[1] : "";
                
                // Generate a random password for the new user
                String randomPassword = generateRandomPassword();
                
                // Make sure all fields are valid
                if (firstName == null || firstName.trim().isEmpty()) {
                    firstName = "Google";
                }
                
                if (lastName == null || lastName.trim().isEmpty()) {
                    lastName = "User";
                }
                
                try {
                    // Create the user with all required fields
                    Utilisateur newUser = new Utilisateur(
                            firstName,
                            lastName,
                            email,
                            randomPassword,
                            "", // No phone number from Google
                            "utilisateur", // Always set role to "utilisateur" for Google logins
                            "Google Login", // Security question
                            "Google User" // Security answer
                    );
                    
                    System.out.println("Attempting to register Google user in database");
                    // Register the new user
                    boolean success = utilisateurService.register(newUser);
                    if (success) {
                        System.out.println("Google user registration successful");
                        return utilisateurService.getUtilisateurByEmail(email);
                    } else {
                        System.out.println("Google user registration failed");
                        // Return a temporary user object anyway so the user can still login
                        System.out.println("Creating temporary user session without database persistence");
                        return newUser;
                    }
                } catch (Exception e) {
                    System.out.println("Exception during Google user registration: " + e.getMessage());
                    e.printStackTrace();
                    
                    // Create a temporary in-memory user object to allow login even with database issues
                    System.out.println("Creating temporary user session due to database connection error");
                    Utilisateur tempUser = new Utilisateur(
                            firstName,
                            lastName,
                            email,
                            randomPassword,
                            "",
                            "utilisateur",
                            "Google Login",
                            "Google User"
                    );
                    return tempUser;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Failed to process Google user information: " + e.getMessage());
        }
    }
    
    private String[] splitName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return new String[]{"Google", "User"};
        }
        
        String[] parts = fullName.trim().split("\\s+");
        
        if (parts.length == 1) {
            return new String[]{parts[0], ""}; // Only first name
        } else if (parts.length == 2) {
            return parts; // First and last name
        } else {
            // More than two parts, combine all but the first into the last name
            StringBuilder lastName = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                if (i > 1) lastName.append(" ");
                lastName.append(parts[i]);
            }
            return new String[]{parts[0], lastName.toString()};
        }
    }
    
    private String generateRandomPassword() {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialChars = "!@#$%^&*()_-+=<>?";
        String allChars = upperChars + lowerChars + numbers + specialChars;
        
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        
        // Ensure at least one character from each category
        password.append(upperChars.charAt(random.nextInt(upperChars.length())));
        password.append(lowerChars.charAt(random.nextInt(lowerChars.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialChars.charAt(random.nextInt(specialChars.length())));
        
        // Fill the rest of the password
        for (int i = 4; i < 12; i++) {
            password.append(allChars.charAt(random.nextInt(allChars.length())));
        }
        
        // Shuffle the password
        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int j = random.nextInt(passwordArray.length);
            char temp = passwordArray[i];
            passwordArray[i] = passwordArray[j];
            passwordArray[j] = temp;
        }
        
        return new String(passwordArray);
    }
} 