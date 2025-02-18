module pidev {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens Controllers to javafx.fxml;
    opens org.example to javafx.fxml;
    exports org.example;


}