package com.esprit.gu;

import java.sql.Connection;
import com.esprit.gu.util.DBUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DataBaseConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null) {
                System.out.println("Connected to the database successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (Exception ex) {
            System.out.println("An error occurred while trying to connect:");
            ex.printStackTrace();
        }
    }


}
