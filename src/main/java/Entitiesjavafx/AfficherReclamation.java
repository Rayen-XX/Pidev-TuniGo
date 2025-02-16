/*package Entitiesjavafx;

import entities.Reclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tools.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AfficherReclamation {

    @FXML
    private TableView<Reclamation> tableReclamations;

    @FXML
    private TableColumn<Reclamation, Integer> colId;

    @FXML
    private TableColumn<Reclamation, String> colDescription;

    @FXML
    private TableColumn<Reclamation, String> colStatut;

    @FXML
    private TableColumn<Reclamation, String> colDate;

    @FXML
    private TableColumn<Reclamation, String> colNom;

    @FXML
    private TableColumn<Reclamation, String> colPrenom;

    @FXML
    private TableColumn<Reclamation, Void> colActions;

    private ObservableList<Reclamation> reclamationsList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        configurerTable();
        chargerReclamations();
    }

    private void configurerTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idReclamation"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("descriptionReclamation"));
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statutReclamation"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("dateReclamation"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nomUtilisateur"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenomUtilisateur"));

        colActions.setCellFactory(param -> createActionCell());

        tableReclamations.setItems(reclamationsList);
    }

    private void chargerReclamations() {
        reclamationsList.clear();
        String query = "SELECT * FROM Reclamation";

        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                reclamationsList.add(new Reclamation(
                        rs.getInt("idReclamation"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("prenom_utilisateur"),
                        rs.getString("typeReclamation"),
                        rs.getString("descriptionReclamation"),
                        rs.getString("statutReclamation"),
                        rs.getDate("dateReclamation")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
           showAlert("Erreur", "Impossible de charger les réclamations.");
        }
    }

    private TableCell<Reclamation, Void> createActionCell() {
        return new TableCell<>() {
            private final ImageView deleteIcon = new ImageView();
            private final ImageView viewIcon = new ImageView();
            private final HBox actionBox = new HBox(10, viewIcon, deleteIcon);

            {
                try {
                    deleteIcon.setImage(new Image(getClass().getResourceAsStream("/icons/delete.png")));
                    viewIcon.setImage(new Image(getClass().getResourceAsStream("/icons/view.png")));
                } catch (Exception e) {
                    System.out.println("Erreur de chargement des icônes: " + e.getMessage());
                }

                deleteIcon.setFitWidth(20);
                deleteIcon.setFitHeight(20);
                deleteIcon.setOnMouseClicked(event -> supprimerReclamation(getTableView().getItems().get(getIndex())));

                viewIcon.setFitWidth(20);
                viewIcon.setFitHeight(20);
                viewIcon.setOnMouseClicked(event -> afficherReclamation(getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }
        };
    }

    private void supprimerReclamation(Reclamation reclamation) {
        String query = "DELETE FROM Reclamation WHERE idReclamation = ?";
        try (Connection conn = DataSource.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, reclamation.getIdReclamation());
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                reclamationsList.remove(reclamation);
                showAlert("Succès", "Réclamation supprimée avec succès.");
            } else {
                showAlert("Erreur", "Impossible de supprimer la réclamation.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors de la suppression.");
        }
    }

    private void afficherReclamation(Reclamation reclamation) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de la Réclamation");
        alert.setHeaderText(null);
        alert.setContentText("ID: " + reclamation.getIdReclamation() +
                "\nNom: " + reclamation.getNom_utilisateur() +
                "\nPrénom: " + reclamation.getPrenom_utilisateur(rs.getString("prenom_utilisateur")) +
                "\nDescription: " + reclamation.getDescriptionReclamation() +
                "\nStatut: " + reclamation.getStatutReclamation() +
                "\nDate: " + reclamation.getDateReclamation());
        alert.showAndWait();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
*/
