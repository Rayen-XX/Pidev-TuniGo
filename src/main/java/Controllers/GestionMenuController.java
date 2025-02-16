package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class GestionMenuController {

    private void changerScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToBus(ActionEvent event) { changerScene(event, "/test/GestionBus.fxml"); }
    public void goToTrain(ActionEvent event) { changerScene(event, "/test/GestionTrain.fxml"); }
    public void goToMetro(ActionEvent event) { changerScene(event, "/test/GestionMetro.fxml"); }
    public void goToScooter(ActionEvent event) { changerScene(event, "/test/GestionScooter.fxml"); }
    public void goToTaxi(ActionEvent event) { changerScene(event, "/test/GestionTaxi.fxml"); }
    public void goToReclamation(ActionEvent event) { changerScene(event, "/test/GestionReclamation.fxml"); }
    public void goToReclamationuser(ActionEvent event) { changerScene(event, "/test/AjouterReclamation.fxml"); }
}
