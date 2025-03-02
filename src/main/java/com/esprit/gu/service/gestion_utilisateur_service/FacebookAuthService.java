package com.esprit.gu.service.gestion_utilisateur_service;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.util.RestFBBridge;
import com.esprit.gu.util.Session;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.types.User;
import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

public class FacebookAuthService {
    private static final String CONFIG_FILE = "facebook-config.properties";
    private String appId;
    private String appSecret;
    private String redirectUri;
    private String permissions;
    private UtilisateurService utilisateurService;

    public FacebookAuthService() {
        // Ensure RestFB classes are accessible
        RestFBBridge.ensureRestFBAccess();
        
        loadProperties();
        utilisateurService = new UtilisateurService();
    }

    private void loadProperties() {
        try {
            Properties prop = new Properties();
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("facebook-config.properties");
            
            if (inputStream != null) {
                prop.load(inputStream);
                
                appId = prop.getProperty("facebook.app_id");
                appSecret = prop.getProperty("facebook.app_secret");
                redirectUri = prop.getProperty("facebook.redirect_uri");
                permissions = prop.getProperty("facebook.permissions");
                
                System.out.println("Loaded Facebook properties:");
                System.out.println("App ID: " + appId);
                System.out.println("App Secret: " + (appSecret != null ? "********" : "null"));
                System.out.println("Redirect URI: " + redirectUri);
                System.out.println("Permissions: " + permissions);
                
                inputStream.close();
            } else {
                System.err.println("facebook-config.properties file not found in the classpath");
            }
        } catch (IOException ex) {
            System.err.println("Error loading Facebook configuration: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * Checks if the JavaFX WebView module is available
     * @return true if available, false otherwise
     */
    private boolean isWebViewAvailable() {
        try {
            // Try to load the JavaFX WebView class
            Class.forName("javafx.scene.web.WebView");
            
            // Create a WebView instance to confirm it works
            javafx.scene.web.WebView webView = new javafx.scene.web.WebView();
            System.out.println("WebView is available and can be instantiated");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("WebView class not found: " + e.getMessage());
            return false;
        } catch (NoClassDefFoundError e) {
            System.err.println("WebView class definition not found: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Error checking WebView availability: " + e.getMessage());
            return false;
        }
    }

    public CompletableFuture<Utilisateur> startFacebookLogin(Stage primaryStage) {
        CompletableFuture<Utilisateur> future = new CompletableFuture<>();
        
        System.out.println("Starting Facebook login process using system browser...");
        
        try {
            // Create the OAuth dialog URL - don't URL encode the entire parameters, just the values
            String encodedRedirectUri = java.net.URLEncoder.encode(redirectUri, "UTF-8");
            
            // Remove any potential spaces in permissions
            String cleanPermissions = permissions.trim().replace(" ", "");
            String encodedScope = java.net.URLEncoder.encode(cleanPermissions, "UTF-8");
            
            // Use response_type=token for implicit flow
            String dialogUrl = "https://www.facebook.com/v18.0/dialog/oauth" +
                    "?client_id=" + appId +
                    "&redirect_uri=" + encodedRedirectUri +
                    "&scope=" + encodedScope +
                    "&auth_type=reauthenticate" +
                    "&response_type=token";
            
            System.out.println("Facebook OAuth URL: " + dialogUrl);
            
            // Create a simple local server to receive the redirect
            int port = 8085;
            com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new java.net.InetSocketAddress(port), 0);
            
            // Set a timeout for the server (2 minutes)
            final boolean[] completed = {false};
            new Thread(() -> {
                try {
                    Thread.sleep(120000); // 2 minute timeout
                    if (!completed[0]) {
                        System.out.println("Facebook login timed out");
                        server.stop(0);
                        Platform.runLater(() -> {
                            future.completeExceptionally(new Exception("Facebook login timed out"));
                        });
                    }
                } catch (InterruptedException e) {
                    // Ignore
                }
            }).start();
            
            // Create context for callback handling
            server.createContext("/", exchange -> {
                String requestUri = exchange.getRequestURI().toString();
                String fragment = exchange.getRequestHeaders().getFirst("Referer");
                
                System.out.println("Received callback: " + requestUri);
                if (fragment != null) {
                    System.out.println("Referer header: " + fragment);
                }
                
                // Create a success page with JavaScript to extract the token from the URL fragment
                String response = "<html><head><title>Authentication Successful</title></head>" +
                        "<body><h1>Authentication Completed</h1>" +
                        "<p>You can now close this window and return to the application.</p>" +
                        "<script>" +
                        "  // Extract the access token from the URL fragment and send it to the server\n" +
                        "  var hash = window.location.hash.substring(1);\n" +
                        "  if (hash) {\n" +
                        "    var xhr = new XMLHttpRequest();\n" +
                        "    xhr.open('GET', '/token?' + hash, true);\n" +
                        "    xhr.send();\n" +
                        "  }\n" +
                        "  // Close the window after a short delay\n" +
                        "  setTimeout(function() { window.close(); }, 1500);\n" +
                        "</script></body></html>";
                
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, response.length());
                try (java.io.OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            });
            
            // Add a second endpoint to receive the token from JavaScript
            server.createContext("/token", exchange -> {
                String query = exchange.getRequestURI().getQuery();
                System.out.println("Token endpoint called with: " + query);
                
                // Send a simple response
                String response = "Token received";
                exchange.getResponseHeaders().set("Content-Type", "text/plain");
                exchange.sendResponseHeaders(200, response.length());
                try (java.io.OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
                
                // Process the access token
                if (query != null && query.contains("access_token=")) {
                    // Extract the token
                    String token = extractParameter(query, "access_token");
                    if (token != null) {
                        System.out.println("Access token found: " + token.substring(0, Math.min(10, token.length())) + "...");
                        
                        // Use the token to get user info
                        new Thread(() -> {
                            try {
                                FacebookClient client = new DefaultFacebookClient(token, Version.LATEST);
                                User facebookUser = client.fetchObject("me", User.class, 
                                        Parameter.with("fields", "id,name,email,first_name,last_name"));
                                
                                String email = facebookUser.getEmail();
                                if (email == null) {
                                    throw new Exception("Email not provided by Facebook");
                                }
                                
                                System.out.println("Retrieved Facebook user email: " + email);
                                
                                Utilisateur utilisateur = processUserInfo(facebookUser);
                                completed[0] = true;
                                server.stop(0);
                                Platform.runLater(() -> future.complete(utilisateur));
                            } catch (Exception e) {
                                System.out.println("Error processing Facebook user: " + e.getMessage());
                                e.printStackTrace();
                                completed[0] = true;
                                server.stop(0);
                                Platform.runLater(() -> future.completeExceptionally(e));
                            }
                        }).start();
                    } else {
                        System.out.println("Could not extract access token from URL");
                        completed[0] = true;
                        server.stop(0);
                        Platform.runLater(() -> future.completeExceptionally(new Exception("Access token not found in URL")));
                    }
                } else if (query.contains("error=")) {
                    // Handle error from Facebook
                    String error = extractParameter(query, "error");
                    String errorDescription = extractParameter(query, "error_description");
                    System.out.println("Error from Facebook: " + error + " - " + errorDescription);
                    completed[0] = true;
                    server.stop(0);
                    Platform.runLater(() -> future.completeExceptionally(new Exception("Facebook error: " + errorDescription)));
                } else {
                    System.out.println("No access token or error found in callback");
                    completed[0] = true;
                    server.stop(0);
                    Platform.runLater(() -> future.completeExceptionally(new Exception("No access token found in callback")));
                }
            });
            
            server.setExecutor(null);
            server.start();
            System.out.println("Local server started on port " + port);
            
            // Open the browser with the login URL - back to normal browser
            java.awt.Desktop.getDesktop().browse(new java.net.URI(dialogUrl));
            System.out.println("System browser opened for authentication");
            
        } catch (Exception e) {
            System.out.println("Error starting Facebook login: " + e.getMessage());
            e.printStackTrace();
            future.completeExceptionally(e);
        }
        
        return future;
    }
    
    private String extractParameter(String query, String paramName) {
        if (query == null) return null;
        
        String param = paramName + "=";
        int startIndex = query.indexOf(param);
        if (startIndex == -1) return null;
        
        startIndex += param.length();
        int endIndex = query.indexOf("&", startIndex);
        if (endIndex == -1) endIndex = query.length();
        
        return query.substring(startIndex, endIndex);
    }
    
    private String extractParameterFromFragment(String url, String paramName) {
        int fragmentIndex = url.indexOf("#");
        if (fragmentIndex == -1) return null;
        
        String fragment = url.substring(fragmentIndex + 1);
        return extractParameter(fragment, paramName);
    }
    
    private Utilisateur processUserInfo(User facebookUser) throws Exception {
        String email = facebookUser.getEmail();
        if (email == null) {
            throw new Exception("Email not provided by Facebook");
        }
        
        Utilisateur existingUser = utilisateurService.getUserByEmail(email);
        if (existingUser != null) {
            // User already exists, return them
            return existingUser;
        } else {
            // Create a new user based on Facebook information
            String[] names = splitName(facebookUser.getName());
            String nom = names[0];
            String prenom = names.length > 1 ? names[1] : "";
            
            // Create a random password (user can reset later if needed)
            String randomPassword = generateRandomPassword();
            
            Utilisateur newUser = new Utilisateur(
                    nom, 
                    prenom, 
                    email, 
                    randomPassword, 
                    "", // No phone number from Facebook 
                    "utilisateur", 
                    "Facebook Login", // Security question
                    "Facebook User" // Security answer
            );
            
            // Register the new user
            boolean success = utilisateurService.register(newUser);
            if (success) {
                return utilisateurService.getUserByEmail(email);
            } else {
                throw new Exception("Failed to register new Facebook user");
            }
        }
    }
    
    private String[] splitName(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return new String[]{"Facebook", "User"};
        }
        
        String[] parts = fullName.split(" ");
        if (parts.length == 1) {
            return new String[]{parts[0], ""};
        } else if (parts.length == 2) {
            return parts;
        } else {
            // More than two parts, combine the extras into the last name
            String firstName = parts[0];
            StringBuilder lastName = new StringBuilder();
            for (int i = 1; i < parts.length; i++) {
                lastName.append(parts[i]);
                if (i < parts.length - 1) {
                    lastName.append(" ");
                }
            }
            return new String[]{firstName, lastName.toString()};
        }
    }
    
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = (int)(Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
} 