package controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mysql.cj.jdbc.JdbcConnection;
import com.sun.scenario.animation.shared.FiniteClipEnvelope;
import javafx.beans.InvalidationListener;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import services.ServiceMetro;

public class MetroTrajet implements ObservableArray<MetroTrajet> {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TableView<MetroTrajet> table;

    @FXML
    private TextField tfGarrive;

    @FXML
    private TextField tfGdepart;

    @FXML
    private TextField tfHarive;

    @FXML
    private TextField tfHdepart;
    private PreparedStatement statment;

    public MetroTrajet(String tfGdepartText, String garriveText, String tfHdepartText, String tfHariveText) {

    }

    @FXML
    void ajouterTrajetmetro(ActionEvent event) {

    }

    @FXML
    void annulerTrajetmetro(ActionEvent event) {

    }

    @FXML
    void modifierTrajetmetro(ActionEvent event) {

    }

    @FXML
    void supprimerTrajetmetro(ActionEvent event) {

    }

    public ObservableList<MetroTrajet> getMetroTrajets() {
        // Création d'un nouveau trajet à partir des valeurs saisies dans les TextFields
        MetroTrajet trajet = new MetroTrajet(
                tfGdepart.getText(),
                tfGarrive.getText(),
                tfHdepart.getText(),
                tfHarive.getText()
        );
        // Création d'une instance du service
        ServiceMetro serviceMetro = new ServiceMetro();
        // Ajout du trajet dans la base de données
        serviceMetro.ajouter((entities.MetroTrajet) trajet);
        // Récupération de la liste complète des trajets et conversion en ObservableList
        ObservableList<MetroTrajet> list = FXCollections.observableArrayList(serviceMetro.getAll());
        return list;
    }




    @FXML
    void initialize() {
        assert btnAjouter != null : "fx:id=\"btnAjouter\" was not injected: check your FXML file 'MetroTrajet.fxml'.";
        assert btnAnnuler != null : "fx:id=\"btnAnnuler\" was not injected: check your FXML file 'MetroTrajet.fxml'.";
        assert btnModifier != null : "fx:id=\"btnModifier\" was not injected: check your FXML file 'MetroTrajet.fxml'.";
        assert btnSupprimer != null : "fx:id=\"btnSupprimer\" was not injected: check your FXML file 'MetroTrajet.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'MetroTrajet.fxml'.";
        assert tfGarrive != null : "fx:id=\"tfGarrive\" was not injected: check your FXML file 'MetroTrajet.fxml'.";
        assert tfGdepart != null : "fx:id=\"tfGdepart\" was not injected: check your FXML file 'MetroTrajet.fxml'.";
        assert tfHarive != null : "fx:id=\"tfHarive\" was not injected: check your FXML file 'MetroTrajet.fxml'.";
        assert tfHdepart != null : "fx:id=\"tfHdepart\" was not injected: check your FXML file 'MetroTrajet.fxml'.";

    }
public void initialize(URL url, ResourceBundle rb) {

}

    @Override
    public void addListener(ArrayChangeListener<MetroTrajet> arrayChangeListener) {

    }

    @Override
    public void removeListener(ArrayChangeListener<MetroTrajet> arrayChangeListener) {

    }

    @Override
    public void resize(int i) {

    }

    @Override
    public void ensureCapacity(int i) {

    }

    @Override
    public void trimToSize() {

    }

    @Override
    public void clear() {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
