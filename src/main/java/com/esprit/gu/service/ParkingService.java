package com.esprit.gu.service;

import com.esprit.gu.entity.Parking;
import com.esprit.gu.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParkingService{

    private Connection conn;

    public ParkingService() throws Exception {
        this.conn = DBUtil.getConnection();
    }

    public void create(Parking obj) throws SQLException {
        String sql = "INSERT INTO parking (nomParking, localisationParking, place) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getNomParking());
            pstmt.setString(2, obj.getLocalisationParking());
            pstmt.setInt(3, obj.getPlace());
            pstmt.executeUpdate();
        }
    }

    public void update(Parking obj) throws SQLException {
        String sql = "UPDATE parking SET nomParking = ?, localisationParking = ?, place = ? WHERE idParking = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, obj.getNomParking());
            pstmt.setString(2, obj.getLocalisationParking());
            pstmt.setInt(3, obj.getPlace());
            pstmt.setInt(4, obj.getIdParking());
            pstmt.executeUpdate();
        }
    }

    public void delete(Parking obj) throws SQLException {
        String sql = "DELETE FROM parking WHERE idParking = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getIdParking());
            pstmt.executeUpdate();
        }
    }

    public List<Parking> getAll() throws SQLException {
        String sql = "SELECT * FROM parking";
        List<Parking> parkings = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Parking parking = new Parking();
                parking.setIdParking(rs.getInt("idParking"));
                parking.setNomParking(rs.getString("nomParking"));
                parking.setLocalisationParking(rs.getString("localisationParking"));
                parking.setPlace(rs.getInt("place"));
                parkings.add(parking);
            }
        }
        return parkings;
    }

    public Parking getById(int id) throws SQLException {
        String sql = "SELECT * FROM parking WHERE idParking = ?";
        Parking parking = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    parking = new Parking();
                    parking.setIdParking(rs.getInt("idParking"));
                    parking.setNomParking(rs.getString("nomParking"));
                    parking.setLocalisationParking(rs.getString("localisationParking"));
                    parking.setPlace(rs.getInt("place"));
                }
            }
        }
        return parking;
    }

    public boolean isParkingExists(String nomParking) throws SQLException {
        String sql = "SELECT nomParking FROM parking WHERE nomParking = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nomParking);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}

