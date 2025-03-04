/*package com.esprit.gu.controller;

import com.esprit.gu.entity.Reclamation;
import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.firebaseconfig.NotificationService;
import com.esprit.gu.service.ServiceReclamation;
import com.esprit.gu.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class AjouterReclamation {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private ComboBox<String> cbTypeReclamation;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button submitButton;
    @FXML
    private Button retour;
    @FXML
    private Button btnAnnuler;
    @FXML
    private ImageView notifIcon;

    private final ServiceReclamation serviceReclamation = new ServiceReclamation();
    private final NotificationService notificationService = new NotificationService();

    public AjouterReclamation() throws Exception {
    }

    @FXML
    public void initialize() {
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            nomField.setText(currentUser.getNomUtilisateur());
            prenomField.setText(currentUser.getPrenomUtilisateur());
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun utilisateur n'est connecté !");
        }
        cbTypeReclamation.getItems().addAll("Service", "Disponibilité", "Paiement");
    }

    @FXML
    private void ajouterReclamation(ActionEvent event) {
        if (cbTypeReclamation.getValue() != null && !descriptionField.getText().isEmpty()) {
            Reclamation reclamation = new Reclamation();
            reclamation.setNom_utilisateur(nomField.getText());
            reclamation.setPrenom_utilisateur(prenomField.getText());
            reclamation.setTypeReclamation(cbTypeReclamation.getValue());
            reclamation.setDescriptionReclamation(descriptionField.getText());
            reclamation.setStatutReclamation("En Attente");
            reclamation.setDateReclamation(new Date());

            serviceReclamation.ajouter(reclamation);
            NotificationService.envoyerNotification(String.valueOf(Session.getCurrentUser()), "Nouvelle réclamation reçue de " + reclamation.getNom_utilisateur());

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation ajoutée avec succès !");
            annuler(event);
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs !");
        }
    }

    @FXML
    private void annuler(ActionEvent event) {
        nomField.clear();
        prenomField.clear();
        cbTypeReclamation.getSelectionModel().clearSelection();
        descriptionField.clear();
    }

    @FXML
    private void handleRetour() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/profile.fxml"));
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/


package com.esprit.gu.controller;

import com.esprit.gu.entity.Reclamation;
import com.esprit.gu.entity.Utilisateur;
import com.esprit.gu.firebaseconfig.NotificationService;
import com.esprit.gu.service.ServiceReclamation;
import com.esprit.gu.util.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class AjouterReclamation {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private ComboBox<String> cbTypeReclamation;
    @FXML
    private TextArea descriptionField;
    @FXML
    private Button submitButton;
    @FXML
    private Button retour;
    @FXML
    private Button btnAnnuler;
    @FXML
    private ImageView notifIcon;

    private final ServiceReclamation serviceReclamation = new ServiceReclamation();
    private final NotificationService notificationService = new NotificationService();

    public AjouterReclamation() throws Exception {
    }

    @FXML
    public void initialize() {
        Utilisateur currentUser = Session.getCurrentUser();
        if (currentUser != null) {
            nomField.setText(currentUser.getNomUtilisateur());
            prenomField.setText(currentUser.getPrenomUtilisateur());
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun utilisateur n'est connecté !");
        }
        cbTypeReclamation.getItems().addAll("Service", "Disponibilité", "Paiement");
    }

    @FXML
    private void ajouterReclamation(ActionEvent event) {
        if (cbTypeReclamation.getValue() != null && !descriptionField.getText().isEmpty()) {
            Utilisateur currentUser = Session.getCurrentUser();

            Reclamation reclamation = new Reclamation();
            reclamation.setNom_utilisateur(nomField.getText());
            reclamation.setPrenom_utilisateur(prenomField.getText());
            reclamation.setTypeReclamation(cbTypeReclamation.getValue());
            reclamation.setDescriptionReclamation(descriptionField.getText());
            reclamation.setStatutReclamation("En Attente");
            reclamation.setDateReclamation(new Date());

            serviceReclamation.ajouter(reclamation);
            NotificationService.envoyerNotification(
                    String.valueOf(currentUser),
                    "Nouvelle réclamation reçue de " + reclamation.getNom_utilisateur()
            );

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Réclamation ajoutée avec succès !");
            sendEmail(currentUser.getEmailUtilisateur(), reclamation);
            annuler(event);

        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs !");
        }
    }

    private void sendEmail(String recipient, Reclamation reclamation) {
        final String senderEmail = "chouchoubenfr@gmail.com\n"; // Ton email
        final String senderPassword = "charifabf1942002*"; // Ton mot de passe

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

  /*      Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
*/
        javax.mail.Session session = javax.mail.Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Confirmation de votre réclamation");
            message.setText(
                    "Bonjour " + reclamation.getPrenom_utilisateur() + " " + reclamation.getNom_utilisateur() + ",\n\n" +
                            "Nous avons bien reçu votre réclamation de type : " + reclamation.getTypeReclamation() + ".\n" +
                            "Description : " + reclamation.getDescriptionReclamation() + "\n\n" +
                            "Notre équipe vous répondra dans les plus brefs délais.\n\n" +
                            "Cordialement,\nL'équipe de support."
            );

            Transport.send(message);
            System.out.println("E-mail envoyé avec succès à " + recipient);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de l'e-mail !");
        }
    }

    @FXML
    private void annuler(ActionEvent event) {
        nomField.clear();
        prenomField.clear();
        cbTypeReclamation.getSelectionModel().clearSelection();
        descriptionField.clear();
    }

    @FXML
    private void handleRetour() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/profile.fxml"));
            Stage stage = (Stage) retour.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
