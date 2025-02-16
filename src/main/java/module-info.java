module com.esprit {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // Required for database access

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
