package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DataSource {
    private Connection cnx;
    private static DataSource instance;

    private final String url = "jdbc:mysql://localhost:3306/tunigo_copy";
    private final String user = "root";
    private final String password = "";

    private DataSource() {
        try {
            cnx = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion à la base de données réussie !");
        } catch (SQLException ex) {
            System.out.println("Erreur de connexion à la base de données : " + ex.getMessage());
        }
    }

    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.cnx;
    }
}
