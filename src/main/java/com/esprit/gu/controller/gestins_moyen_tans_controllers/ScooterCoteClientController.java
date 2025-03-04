package com.esprit.gu.controller.gestins_moyen_tans_controllers;

import com.esprit.gu.entity.Scooter;
import com.esprit.gu.service.ServiceScooter;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class ScooterCoteClientController {
    @FXML
    private VBox scooterGridContainer;

    private ServiceScooter serviceScooter = new ServiceScooter();

    public ScooterCoteClientController() throws Exception {}

    @FXML
    public void initialize() {
        loadScooterCards();
    }

    private void loadScooterCards() {
        List<Scooter> scooters = serviceScooter.getAll();
        scooterGridContainer.getChildren().clear();

        HBox row = new HBox(20);
        int cardsPerRow = 3;
        int count = 0;

        for (Scooter scooter : scooters) {
            VBox scooterCard = createScooterCard(scooter);
            row.getChildren().add(scooterCard);
            count++;

            if (count % cardsPerRow == 0) {
                scooterGridContainer.getChildren().add(row);
                row = new HBox(20);
            }
        }

        // Ajouter la dernière ligne même si elle n'est pas complète
        if (!row.getChildren().isEmpty()) {
            scooterGridContainer.getChildren().add(row);
        }
    }

    private VBox createScooterCard(Scooter scooter) {

        VBox card = new VBox();
        card.getStyleClass().add("scooter-card");

        HBox row = new HBox(30); // Définit un espacement horizontal de 20px entre les cartes
        row.setAlignment(Pos.CENTER); // Centre les cartes horizontalement

        ImageView imageView = new ImageView(new Image("C:\\3A18\\Java\\Pidev-TuniGo-integration - Copie020032025\\Pidev-TuniGo-integration - Copie\\src\\main\\resources\\views\\Images\\scooter4.png"));
        imageView.setFitHeight(130);
        imageView.setFitWidth(200);

        HBox numeroScooterBox = createInfoRow("Numéro Scooter:", scooter.getNumeroScooter());
        HBox localisationScooterBox = createInfoRow("Localisation:", scooter.getLocalisationScooter());
        HBox disponibiliteBox = createInfoRow("Disponibilité:", scooter.isIsdisponible() ? "Disponible" : "Indisponible");

        Button reserveButton = new Button("Réserver");
        reserveButton.setOnAction(e -> handleReserve(scooter));

        card.getChildren().addAll(imageView, numeroScooterBox, localisationScooterBox, disponibiliteBox, reserveButton);
        return card;
    }

    private HBox createInfoRow(String labelText, String contentText) {
        HBox infoRow = new HBox();
        infoRow.getStyleClass().add("info-container");

        Label label = new Label(labelText);
        label.getStyleClass().add("info-label");

        Label content = new Label(contentText);
        content.getStyleClass().add("info-content");

        infoRow.getChildren().addAll(label, content);
        return infoRow;
    }

    private void handleReserve(Scooter scooter) {
        System.out.println("Réservation du scooter: " + scooter.getNumeroScooter());
    }
}
