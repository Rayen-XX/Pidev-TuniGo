package com.esprit.gu.controller.gestins_moyen_tans_controllers;

import com.esprit.gu.entity.Taxi;
import com.esprit.gu.service.ServiceTaxi;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class TaxiCoteClientController {
    @FXML
    private HBox taxiCardsContainer;

    private ServiceTaxi serviceTaxi = new ServiceTaxi();

    public TaxiCoteClientController() throws Exception {}

    @FXML
    public void initialize() {
        loadTaxiCards();
    }

    private void loadTaxiCards() {
        List<Taxi> taxis = serviceTaxi.getAll();
        taxiCardsContainer.getChildren().clear();

        for (Taxi taxi : taxis) {
            VBox taxiCard = createTaxiCard(taxi);
            taxiCardsContainer.getChildren().add(taxiCard);
        }
    }

    private VBox createTaxiCard(Taxi taxi) {
        VBox card = new VBox();
        card.getStyleClass().add("taxi-card");

        ImageView imageView = new ImageView(new Image("file:/C:/3A18/Java/Pidev-TuniGo-integration - Copie020032025/Pidev-TuniGo-integration - Copie/src/main/resources/Images/TuniGo_Logo.png"));
        imageView.setFitHeight(120);
        imageView.setFitWidth(250);

        HBox numeroTaxiBox = createInfoRow("Numéro Taxi:", taxi.getNumeroTaxi());
        //HBox numeroChauffeurBox = createInfoRow("Numéro Chauffeur:", taxi.getNumeroChauffeur());
        HBox prenomChauffeurBox = createInfoRow("Prénom:", taxi.getPrenomChauffeur());
        HBox nomChauffeurBox = createInfoRow("Nom:", taxi.getNomChauffeur());

        Button reserveButton = new Button("Réserver");
        reserveButton.setOnAction(e -> handleReserve(taxi));

        card.getChildren().addAll(imageView, numeroTaxiBox,/* numeroChauffeurBox, */prenomChauffeurBox, nomChauffeurBox, reserveButton);
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


    private void handleReserve(Taxi taxi) {
        System.out.println("Réservation du taxi: " + taxi.getNumeroTaxi());
    }
}