package com.esprit.gu.controller;

import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.service.UtilisateurService;
import com.esprit.gu.util.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDashboardGUController implements Initializable {

    @FXML
    private ListView<Utilisateur> usersList;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Hyperlink logoutLink;

    @FXML
    private Button retour;

    private UtilisateurService utilisateurService = new UtilisateurService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUsers();


        // Set a custom cell factory to display user details on one line.
        usersList.setCellFactory(lv -> new ListCell<Utilisateur>() {
            @Override
            protected void updateItem(Utilisateur user, boolean empty) {
                super.updateItem(user, empty);
                if (empty || user == null) {
                    setText(null);
                } else {
                    setText("ID: " + user.getIdUtilisateur() +
                            " | Nom: " + user.getNomUtilisateur() +
                            " | Prenom: " + user.getPrenomUtilisateur() +
                            " | Email: " + user.getEmailUtilisateur() +
                            " | Phone: " + user.getNumeroTelephoneUtilisateur() +
                            " | Role: " + user.getRoleUtilisateur());

                    setStyle(
                            "-fx-background-radius: 8px; " +
                            "-fx-padding: 10px; " +
                            "-fx-border-color: #dddddd; " +
                            "-fx-border-radius: 8px;");
                }
            }
        });
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            welcomeLabel.setText("Bonjour, " + currentUser.getNomUtilisateur() + " "
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
                Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
                stage.setScene(new Scene(root));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void loadUsers() {
        List<Utilisateur> userList = utilisateurService.getAllUsers();
        ObservableList<Utilisateur> usersObservable = FXCollections.observableArrayList(userList);
        usersList.setItems(usersObservable);
    }

    @FXML
    private void handleRefresh() {
        loadUsers();
    }

    @FXML
    private void handleDeleteUser() {
        Utilisateur selectedUser = usersList.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            boolean success = utilisateurService.deleteUtilisateur(selectedUser.getIdUtilisateur());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Utilisateur supprimé", "Utilisateur supprimé avec succès.");
                loadUsers();
            } else {
                showAlert(Alert.AlertType.ERROR, "Échec de la suppression", "Échec de la suppression de l'utilisateur.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un utilisateur à supprimer.");
        }
    }

    @FXML
    private void handleCreateUser() {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/views/createUser.fxml"));
            stage.setScene(new Scene(root));
            stage.setTitle("Create User");
            stage.showAndWait();
            loadUsers();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir la fenêtre de création d'utilisateur.");
        }
    }



    @FXML
    private void handleUpdateUser() {
        Utilisateur selectedUser = usersList.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/updateUser.fxml"));
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
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ouvrir la fenêtre de mise à jour de l'utilisateur.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un utilisateur à mettre à jour.");
        }
    }

    @FXML
    private void handleRetour() {
        try {
            // Load the admin_dashboard_gu.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/views/admin_dashboard_all.fxml"));
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
