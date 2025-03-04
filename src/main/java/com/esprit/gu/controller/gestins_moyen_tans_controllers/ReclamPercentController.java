package com.esprit.gu.controller.gestins_moyen_tans_controllers;

import com.esprit.gu.service.ServiceReclamation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.util.Map;

public class ReclamPercentController {

    @FXML
    private PieChart pieChart;

    private ServiceReclamation serviceReclamation;

    public ReclamPercentController() {
        try {
            serviceReclamation = new ServiceReclamation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        Map<String, Integer> reclamations = serviceReclamation.getReclamationsCountByType();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        int total = reclamations.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : reclamations.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / total;
            pieChartData.add(new PieChart.Data(entry.getKey() + " " + String.format("%.1f", percentage) + "%", entry.getValue()));
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Répartition des Réclamations");
    }
}