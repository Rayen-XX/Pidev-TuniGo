package com.esprit.gu.controller.gestion_utilisateur_controllers;

import com.esprit.gu.entity.gestion_utilisateur_entity.Utilisateur;
import com.esprit.gu.service.gestion_utilisateur_service.UtilisateurService;
import com.esprit.gu.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdminDashboardGUController implements Initializable {

    @FXML
    private ListView<Utilisateur> usersList;

    @FXML
    private TextField searchField;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Hyperlink logoutLink;

    @FXML
    private Button retour;

    private UtilisateurService utilisateurService = new UtilisateurService();
    private ObservableList<Utilisateur> allUsers;
    private FilteredList<Utilisateur> filteredUsers;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Load all users initially
        loadUsers();
        
        // Set up search functionality
        setupSearch();

        // Set a custom cell factory with improved layout for user details
        usersList.setCellFactory(lv -> new ListCell<Utilisateur>() {
            @Override
            protected void updateItem(Utilisateur user, boolean empty) {
                super.updateItem(user, empty);
                
                if (empty || user == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a structured layout for user information
                    VBox container = new VBox(5); // 5px spacing between elements
                    
                    // Main user info (ID, name, contact)
                    HBox mainInfo = new HBox(15); // 15px spacing
                    
                    // Create ID label with bold styling
                    Label idLabel = new Label("ID: " + user.getIdUtilisateur());
                    idLabel.getStyleClass().add("bold-label");
                    
                    // Create name label with bold styling
                    Label nameLabel = new Label(user.getNomUtilisateur() + " " + user.getPrenomUtilisateur());
                    nameLabel.getStyleClass().add("bold-label");
                    
                    // Create regular info labels
                    Label emailLabel = new Label("Email: " + user.getEmailUtilisateur());
                    Label phoneLabel = new Label("Tél: " + user.getNumeroTelephoneUtilisateur());
                    
                    // Create role label with special styling
                    Label roleLabel = new Label("Role: " + user.getRoleUtilisateur());
                    roleLabel.getStyleClass().add("role-label");
                    
                    mainInfo.getChildren().addAll(idLabel, nameLabel, emailLabel, phoneLabel, roleLabel);
                    
                    // Security information (question & answer)
                    HBox securityInfo = new HBox(15);
                    securityInfo.setStyle("-fx-padding: 0 0 0 20;"); // Add left padding
                    
                    // Create security question label
                    Label questionLabel = new Label("Question de sécurité: " + 
                            (user.getQuestionSecurite() != null ? user.getQuestionSecurite() : "N/A"));
                    questionLabel.getStyleClass().add("security-info");
                    
                    // Create security answer label
                    Label answerLabel = new Label("Réponse: " + 
                            (user.getReponseSecurite() != null ? user.getReponseSecurite() : "N/A"));
                    answerLabel.getStyleClass().add("security-info");
                    
                    securityInfo.getChildren().addAll(questionLabel, answerLabel);
                    
                    // Add all information to the container
                    container.getChildren().addAll(mainInfo, securityInfo);
                    
                    // Use the container as the cell's graphic
                    setGraphic(container);
                    setText(null); // We're using graphic instead of text
                }
            }
        });
        
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText(currentUser.getNomUtilisateur() + " "
                    + currentUser.getPrenomUtilisateur());
        } else {
            welcomeLabel.setText("Utilisateur non connecté");
        }

        // Set up logout action.
        logoutLink.setOnAction(e -> {
            Session.clear();  // Clear the current session
            try {
                // Load the login view and set it as the current scene.
                Stage stage = (Stage) logoutLink.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/login.fxml"));
                stage.setScene(new Scene(root));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
    
    /**
     * Set up the search functionality for filtering users by email
     */
    private void setupSearch() {
        // Initialize filtered list with all users
        filteredUsers = new FilteredList<>(allUsers, p -> true);
        
        // Bind the filtered list to the ListView
        usersList.setItems(filteredUsers);
        
        // Add listener to search field to update filter predicate on text change
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                // If search field is empty, show all users
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                // Compare email with filter text (case-insensitive)
                String lowerCaseFilter = newValue.toLowerCase();
                String email = user.getEmailUtilisateur().toLowerCase();
                
                return email.contains(lowerCaseFilter);
            });
        });
    }

    private void loadUsers() {
        List<Utilisateur> userList = utilisateurService.getAllUsers();
        allUsers = FXCollections.observableArrayList(userList);
        
        // If the filtered list already exists, update its source
        if (filteredUsers != null) {
            filteredUsers = new FilteredList<>(allUsers, filteredUsers.getPredicate());
            usersList.setItems(filteredUsers);
        } else {
            // First load, set items directly
            usersList.setItems(allUsers);
        }
    }

    @FXML
    private void handleRefresh() {
        // Store current search text
        String currentSearch = searchField.getText();
        
        // Reload users
        loadUsers();
        
        // Reapply the search if needed
        if (currentSearch != null && !currentSearch.isEmpty()) {
            searchField.setText(currentSearch);
        }
    }

    @FXML
    private void handleDeleteUser() {
        Utilisateur selectedUser = usersList.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            boolean success = utilisateurService.deleteUtilisateur(selectedUser.getIdUtilisateur());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "User Deleted", "User deleted successfully.");
                loadUsers();
            } else {
                showAlert(Alert.AlertType.ERROR, "Deletion Failed", "Failed to delete user.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to delete.");
        }

        
    }

    @FXML
    private void handleCreateUser() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/createUser.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Create User");
            stage.showAndWait();
            loadUsers();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Could not open the create user window.");
        }
    }



    @FXML
    private void handleUpdateUser() {
        Utilisateur selectedUser = usersList.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/gestion_utilisateur_views/updateUser.fxml"));
                Parent root = loader.load();

                // Pass the selected user to the update controller.
                UpdateUserController updateController = loader.getController();
                updateController.setUser(selectedUser);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update User");
                stage.showAndWait();

                loadUsers();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Could not open the update user window.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a user to update.");
        }
    }

    @FXML
    private void handleRetour() {
        try {
            // Load the admin_dashboard_gu.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/views/gestion_utilisateur_views/admin_dashboard_all.fxml"));
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
