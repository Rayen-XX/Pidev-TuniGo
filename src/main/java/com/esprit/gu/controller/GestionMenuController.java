package com.esprit.gu.controller;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionMenuController {
    public GestionMenuController() {
    }

    private void changerScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fxmlFile));
            Parent root = (Parent)loader.load();
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException var6) {
            IOException e = var6;
            e.printStackTrace();
        }

    }

    public void goToBus(ActionEvent event) {
        this.changerScene(event, "/views/GestionBus.fxml");
    }

    public void goToTrain(ActionEvent event) {
        this.changerScene(event, "/views/GestionTrain.fxml");
    }

    public void goToMetro(ActionEvent event) {
        this.changerScene(event, "/views/GestionMetro.fxml");
    }

    public void goToScooter(ActionEvent event) {
        this.changerScene(event, "/views/GestionScooter.fxml");
    }

    public void goToTaxi(ActionEvent event) {
        this.changerScene(event, "/views/GestionTaxi.fxml");
    }

    public void goToReclamation(ActionEvent event) {
        this.changerScene(event, "/views/GestionReclamation.fxml");
    }

    public void goToReclamationuser(ActionEvent event) {
        this.changerScene(event, "/views/AjouterReclamation.fxml");
    }
    public void handleRetour(ActionEvent event) {
        this.changerScene(event, "/views/admin_dashboard_all.fxml");
    }


}
