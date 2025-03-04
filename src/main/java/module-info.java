module com.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires com.google.api.apicommon;
    requires com.google.auth;
    requires java.desktop;
    requires QuickChart;
    requires unirest.java;
    requires javafx.web;
    requires java.mail;

    opens com.esprit.gu to javafx.fxml;
    opens com.esprit.gu.controller to javafx.fxml;
    opens com.esprit.gu.repository to javafx.fxml;
    opens com.esprit.gu.entity to javafx.base, javafx.fxml;
    opens com.esprit.gu.util to javafx.fxml;

    exports com.esprit.gu;
    exports com.esprit.gu.controller;
    exports com.esprit.gu.repository;
    exports com.esprit.gu.util;
}
