 package main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MetroTrajet.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Test CRUD");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


}
