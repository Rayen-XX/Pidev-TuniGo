package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    public static final String URL = "jdbc:mysql://localhost:3306/tunigocopie";
    public static final String USER = "root";
    public static final String PASSWORD = "";
    public static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connexion réussie à la DB !");
            } catch (SQLException e) {
                System.err.println("Erreur de connexion : " + e.getMessage());
            }
        }
        return connection;
    }


    public static Object getInstance() {
        return null;
    }
}
