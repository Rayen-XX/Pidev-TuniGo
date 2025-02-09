package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    private String url = "jdbc:mysql://localhost:3306/3a21_codex_pi";
    private String user = "root";
    private String password = "";
    private Connection conn;
    private static MyDB instance;

    public static MyDB getInstance() {
        if (instance == null) {
            instance = new MyDB();
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }

    private MyDB() {
        try {
            this.conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection established");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
