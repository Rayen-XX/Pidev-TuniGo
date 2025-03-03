package com.esprit.gu.service.gestion_utilisateur_service;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.entity.gestion_utilisateur_entity.enums.AuthProvider;
import com.esprit.gu.util.json.JSONObject;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Service for handling GitHub OAuth authentication
 */
public class GitHubAuthService {
    private static final String CONFIG_FILE = "github-config.properties";
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String scope;
    private UtilisateurService utilisateurService;

    /**
     * Constructor that initializes the GitHub authentication service by loading properties
     */
    public GitHubAuthService() {
        loadProperties();
        this.utilisateurService = new UtilisateurService();
    }

    /**
     * Loads the GitHub OAuth configuration from properties file
     */
    private void loadProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (inputStream == null) {
                System.err.println("Unable to find " + CONFIG_FILE);
                return;
            }
            properties.load(inputStream);
            clientId = properties.getProperty("github.client.id");
            clientSecret = properties.getProperty("github.client.secret");
            redirectUri = properties.getProperty("github.redirect.uri");
            scope = properties.getProperty("github.auth.scope");
            
            System.out.println("GitHub properties loaded successfully!");
        } catch (Exception e) {
            System.err.println("Error loading GitHub properties: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Checks if WebView is available for OAuth flow
     */
    private boolean isWebViewAvailable() {
        try {
            Class.forName("javafx.scene.web.WebView");
            return true;
        } catch (ClassNotFoundException e) {
            System.err.println("WebView is not available: " + e.getMessage());
            return false;
        }
    }

    /**
     * Starts the GitHub login process
     * @param primaryStage The primary stage from the JavaFX application
     * @return A CompletableFuture that will resolve to a User when authentication is complete
     */
    public CompletableFuture<Utilisateur> startGitHubLogin(Stage primaryStage) {
        CompletableFuture<Utilisateur> future = new CompletableFuture<>();
        
        if (!isWebViewAvailable()) {
            future.completeExceptionally(new Exception("WebView is not available. Cannot proceed with GitHub authentication."));
            return future;
        }
        
        try {
            // Create a WebView for the GitHub authentication
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();
            
            // Create a new stage for the WebView
            Stage webStage = new Stage();
            webStage.setTitle("GitHub Login");
            webStage.setScene(new Scene(webView, 800, 600));
            
            // Generate a random state value to prevent CSRF attacks
            String state = generateRandomString(16);
            
            // Build the GitHub authorization URL
            String authUrl = "https://github.com/login/oauth/authorize" +
                    "?client_id=" + clientId +
                    "&redirect_uri=" + redirectUri +
                    "&scope=" + scope +
                    "&state=" + state;
            
            System.out.println("Navigating to GitHub WebView with URL: " + authUrl);
            
            // Load the GitHub authorization URL
            webEngine.load(authUrl);
            
            // Handle the redirect to extract the authorization code
            webEngine.locationProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("WebView redirected to: " + newValue);
                
                if (newValue != null && newValue.startsWith(redirectUri)) {
                    System.out.println("Detected redirect URI, extracting code...");
                    try {
                        // Close the WebView stage
                        webStage.close();
                        
                        // Extract the authorization code from the URL
                        String authorizationCode = extractCodeFromUrl(newValue);
                        
                        if (authorizationCode != null) {
                            System.out.println("Authorization code received, exchanging for token...");
                            
                            // Create a new task for getting the token and user info
                            Task<Utilisateur> authTask = new Task<Utilisateur>() {
                                @Override
                                protected Utilisateur call() throws Exception {
                                    try {
                                        // Exchange the code for an access token
                                        JSONObject tokenResponse = exchangeCodeForToken(authorizationCode);
                                        
                                        if (tokenResponse != null && tokenResponse.has("access_token")) {
                                            String accessToken = tokenResponse.getString("access_token");
                                            System.out.println("Access token received, fetching user info...");
                                            
                                            // Get the user information using the access token
                                            JSONObject userInfo = getUserInfo(accessToken);
                                            
                                            if (userInfo != null) {
                                                System.out.println("User info received, processing...");
                                                
                                                // Process the user information and return a User
                                                return processUserInfo(userInfo);
                                            } else {
                                                System.err.println("Failed to get user info");
                                                return null;
                                            }
                                        } else {
                                            System.err.println("Failed to get access token");
                                            if (tokenResponse != null) {
                                                System.err.println("Token response: " + tokenResponse.toString());
                                            }
                                            return null;
                                        }
                                    } catch (Exception e) {
                                        System.err.println("Error in GitHub authentication task: " + e.getMessage());
                                        e.printStackTrace();
                                        throw e;
                                    }
                                }
                            };
                            
                            // Handle the task completion
                            authTask.setOnSucceeded(event -> {
                                Utilisateur user = authTask.getValue();
                                future.complete(user);
                            });
                            
                            authTask.setOnFailed(event -> {
                                future.completeExceptionally(authTask.getException());
                            });
                            
                            // Start the authentication task
                            new Thread(authTask).start();
                        } else {
                            System.err.println("Failed to extract authorization code from URL");
                            future.completeExceptionally(new Exception("Failed to extract authorization code"));
                        }
                    } catch (Exception e) {
                        System.err.println("Error processing redirect: " + e.getMessage());
                        e.printStackTrace();
                        future.completeExceptionally(e);
                    }
                }
            });
            
            // Show the WebView stage
            webStage.show();
            
        } catch (Exception e) {
            System.err.println("Error starting GitHub login: " + e.getMessage());
            e.printStackTrace();
            future.completeExceptionally(e);
        }
        
        return future;
    }

    /**
     * Extracts the authorization code from the redirect URL
     */
    private String extractCodeFromUrl(String url) {
        try {
            // Try to extract from query parameters
            String decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.name());
            System.out.println("Decoded URL: " + decodedUrl);
            
            // Extract the code from query parameters
            Pattern pattern = Pattern.compile("[?&]code=([^&]+)");
            Matcher matcher = pattern.matcher(decodedUrl);
            
            if (matcher.find()) {
                String code = matcher.group(1);
                System.out.println("Extracted code from query params: " + code);
                return code;
            }
            
            // If not found in query params, check URL fragment
            if (decodedUrl.contains("#")) {
                String fragment = decodedUrl.substring(decodedUrl.indexOf('#') + 1);
                String code = extractParameterFromFragment(fragment, "code");
                
                if (code != null && !code.isEmpty()) {
                    System.out.println("Extracted code from URL fragment: " + code);
                    return code;
                }
            }
            
            System.err.println("Could not extract code from URL: " + decodedUrl);
            return null;
        } catch (Exception e) {
            System.err.println("Error extracting code from URL: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Extracts parameters from URL fragment
     */
    private String extractParameterFromFragment(String fragment, String paramName) {
        String[] params = fragment.split("&");
        for (String param : params) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2 && keyValue[0].equals(paramName)) {
                return keyValue[1];
            }
        }
        return null;
    }

    /**
     * Parses a JSON string into a JSONObject
     */
    private JSONObject parseJson(String jsonString) {
        JSONObject result = new JSONObject();
        try {
            // Check if string is well-formed JSON
            if (jsonString != null && jsonString.trim().startsWith("{") && jsonString.trim().endsWith("}")) {
                // This is a simple JSON parser, and not intended for production use
                // It handles the basic GitHub API responses we need
                jsonString = jsonString.trim();
                
                // Remove the outer braces
                jsonString = jsonString.substring(1, jsonString.length() - 1).trim();
                
                // Split by commas but respect nested objects/arrays
                int bracketCount = 0;
                int braceCount = 0;
                StringBuilder currentPart = new StringBuilder();
                
                for (int i = 0; i < jsonString.length(); i++) {
                    char c = jsonString.charAt(i);
                    
                    if (c == '{') braceCount++;
                    else if (c == '}') braceCount--;
                    else if (c == '[') bracketCount++;
                    else if (c == ']') bracketCount--;
                    
                    // If we're at a top-level comma, split
                    if (c == ',' && braceCount == 0 && bracketCount == 0) {
                        processJsonKeyValue(currentPart.toString().trim(), result);
                        currentPart = new StringBuilder();
                    } else {
                        currentPart.append(c);
                    }
                }
                
                // Don't forget the last part
                if (currentPart.length() > 0) {
                    processJsonKeyValue(currentPart.toString().trim(), result);
                }
                
                return result;
            } else if (jsonString != null && jsonString.contains("access_token=")) {
                // Handle URL-encoded format responses
                String[] pairs = jsonString.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=");
                    if (keyValue.length == 2) {
                        result.put(keyValue[0], keyValue[1]);
                    }
                }
                return result;
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
            e.printStackTrace();
        }
        
        // If we can't parse it normally, create a simple object with the raw response
        result.put("raw_response", jsonString);
        return result;
    }
    
    /**
     * Helper method to process a JSON key-value pair
     */
    private void processJsonKeyValue(String keyValueStr, JSONObject result) {
        // Find the first colon outside of quotes
        int colonPos = -1;
        boolean inQuotes = false;
        
        for (int i = 0; i < keyValueStr.length(); i++) {
            char c = keyValueStr.charAt(i);
            if (c == '\"') inQuotes = !inQuotes;
            if (c == ':' && !inQuotes) {
                colonPos = i;
                break;
            }
        }
        
        if (colonPos > 0) {
            String key = keyValueStr.substring(0, colonPos).trim();
            String value = keyValueStr.substring(colonPos + 1).trim();
            
            // Remove quotes from key
            if (key.startsWith("\"") && key.endsWith("\"")) {
                key = key.substring(1, key.length() - 1);
            }
            
            // Handle different value types
            if (value.startsWith("\"") && value.endsWith("\"")) {
                // String value
                value = value.substring(1, value.length() - 1);
                result.put(key, value);
            } else if (value.equals("null")) {
                // Null value
                result.put(key, null);
            } else if (value.equals("true") || value.equals("false")) {
                // Boolean value
                result.put(key, Boolean.parseBoolean(value));
            } else {
                try {
                    // Try to parse as number
                    if (value.contains(".")) {
                        result.put(key, Double.parseDouble(value));
                    } else {
                        result.put(key, Integer.parseInt(value));
                    }
                } catch (NumberFormatException e) {
                    // Just store as string if not a number
                    result.put(key, value);
                }
            }
        }
    }

    /**
     * Exchanges the authorization code for an access token
     */
    private JSONObject exchangeCodeForToken(String code) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://github.com/login/oauth/access_token");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            
            // Create the request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("client_id", clientId);
            requestBody.put("client_secret", clientSecret);
            requestBody.put("code", code);
            requestBody.put("redirect_uri", redirectUri);
            
            // Write the request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            
            // Read the response
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                return parseJson(response.toString());
            } else {
                System.err.println("Error exchanging code for token. Response code: " + responseCode);
                
                // Try to read error response
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
                    String line;
                    StringBuilder errorResponse = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    System.err.println("Error response: " + errorResponse.toString());
                } catch (Exception e) {
                    System.err.println("Could not read error response: " + e.getMessage());
                }
                
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error exchanging code for token: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Gets the user info using the access token
     */
    private JSONObject getUserInfo(String accessToken) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://api.github.com/user");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "token " + accessToken);
            connection.setRequestProperty("Accept", "application/json");
            
            int responseCode = connection.getResponseCode();
            
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                StringBuilder response = new StringBuilder();
                
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                return parseJson(response.toString());
            } else {
                System.err.println("Error getting user info. Response code: " + responseCode);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error getting user info: " + e.getMessage());
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Processes the user info from GitHub and creates or retrieves the user
     */
    private Utilisateur processUserInfo(JSONObject userInfo) throws Exception {
        try {
            // Extract user information from the GitHub response
            String login = userInfo.getString("login");
            String name = userInfo.has("name") && !userInfo.isNull("name") ? 
                         userInfo.getString("name") : login;
            
            // Get email separately as it might be private
            String email = null;
            if (userInfo.has("email") && !userInfo.isNull("email")) {
                email = userInfo.getString("email");
            }
            
            // If email is null, we need to make a separate API call to get private emails
            if (email == null || email.isEmpty()) {
                // For simplicity, we'll use the login + dummy domain as email if not available
                email = login + "@github.user";
            }
            
            String avatarUrl = userInfo.has("avatar_url") ? userInfo.getString("avatar_url") : null;
            
            System.out.println("Processing GitHub user: " + name + " with email: " + email);
            
            // Check if the user exists in our system
            Utilisateur existingUser = utilisateurService.getUtilisateurByEmail(email);
            
            if (existingUser != null) {
                System.out.println("User already exists in the system, returning existing user");
                return existingUser;
            } else {
                // Create a new user
                System.out.println("Creating new user for GitHub authentication");
                return createNewUser(name, email, avatarUrl);
            }
        } catch (Exception e) {
            System.err.println("Error processing user info: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Creates a new user with the GitHub information
     */
    private Utilisateur createNewUser(String fullName, String email, String profileImageUrl) {
        try {
            // Split full name into first and last name
            String[] nameParts = splitName(fullName);
            String firstName = nameParts[0];
            String lastName = nameParts[1];
            
            // Generate a random password for the user
            String randomPassword = generateRandomPassword();
            
            // Create and save the new user with all required fields
            Utilisateur newUser = new Utilisateur(
                firstName,
                lastName,
                email,
                randomPassword,
                "", // No phone number from GitHub
                "utilisateur", // Always set role to "utilisateur" for GitHub logins
                "GitHub Login", // Security question
                "GitHub User" // Security answer
            );
            
            System.out.println("Attempting to register GitHub user in database");
            // Register the new user
            boolean success = utilisateurService.register(newUser);
            if (success) {
                System.out.println("GitHub user registration successful");
                return utilisateurService.getUtilisateurByEmail(email);
            } else {
                System.out.println("GitHub user registration failed");
                // Return a temporary user object anyway so the user can still login
                System.out.println("Creating temporary user session without database persistence");
                return newUser;
            }
        } catch (Exception e) {
            System.err.println("Error creating new user: " + e.getMessage());
            e.printStackTrace();
            
            // If an error occurs, still return a temporary user object
            String[] nameParts = splitName(fullName);
            return createTemporaryUser(nameParts[0], nameParts[1], email);
        }
    }

    /**
     * Creates a temporary user object that isn't persisted
     */
    private Utilisateur createTemporaryUser(String firstName, String lastName, String email) {
        // Create a user with a temporary ID
        Utilisateur tempUser = new Utilisateur(
            firstName,
            lastName,
            email,
            generateRandomPassword(),
            "", // No phone number
            "utilisateur", // Default role
            "GitHub Login", // Security question
            "GitHub User" // Security answer
        );
        tempUser.setIdUtilisateur(999); // Set a temporary ID
        
        System.out.println("Created temporary user: " + email);
        return tempUser;
    }

    /**
     * Splits a full name into first and last name parts
     */
    private String[] splitName(String fullName) {
        String firstName = "";
        String lastName = "";
        
        if (fullName != null && !fullName.trim().isEmpty()) {
            String[] nameParts = fullName.trim().split("\\s+");
            
            if (nameParts.length > 0) {
                firstName = nameParts[0];
                
                // If there are multiple parts, combine the rest as the last name
                if (nameParts.length > 1) {
                    StringBuilder lastNameBuilder = new StringBuilder();
                    for (int i = 1; i < nameParts.length; i++) {
                        if (i > 1) {
                            lastNameBuilder.append(" ");
                        }
                        lastNameBuilder.append(nameParts[i]);
                    }
                    lastName = lastNameBuilder.toString();
                } else {
                    // If only one name is provided, use "GitHub" as the last name
                    lastName = "GitHub";
                }
            } else {
                // Default values if the name is empty after trimming
                firstName = "GitHub";
                lastName = "User";
            }
        } else {
            // Default values if the name is null or empty
            firstName = "GitHub";
            lastName = "User";
        }
        
        return new String[]{firstName, lastName};
    }

    /**
     * Generates a random password
     */
    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        // Generate a password with 12 characters
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }

    /**
     * Generates a random string of specified length
     */
    private String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            sb.append(chars.charAt(index));
        }
        
        return sb.toString();
    }
} 