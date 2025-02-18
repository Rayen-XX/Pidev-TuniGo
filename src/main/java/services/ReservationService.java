package services;

import models.Parking;
import models.Reservation;
import utils.MyDB;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
public class ReservationService implements Crud<Reservation> {

    private Connection conn;

    public ReservationService() {
        this.conn = MyDB.getInstance().getConn();
    }

    @Override
    public void create(Reservation obj) throws SQLException {
        String sql = "INSERT INTO reservation (dateReservation, idUtilisateur, idTrajetTrain, idTrajetMetro, idTrajetBus, idScooter, idTaxi, idParking) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Timestamp timestamp = Timestamp.valueOf(obj.getDateReservation());
            pstmt.setTimestamp(1, timestamp);
            pstmt.setInt(2, obj.getIdUtilisateur());
            pstmt.setObject(3, obj.getIdTrajetTrain(), Types.INTEGER);
            pstmt.setObject(4, obj.getIdTrajetMetro(), Types.INTEGER);
            pstmt.setObject(5, obj.getIdTrajetBus(), Types.INTEGER);
            pstmt.setObject(6, obj.getIdScooter(), Types.INTEGER);
            pstmt.setObject(7, obj.getIdTaxi(), Types.INTEGER);
            pstmt.setObject(8, obj.getIdParking(), Types.INTEGER);

            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Reservation obj) throws SQLException {
        String sql = "UPDATE reservation SET dateReservation = ?, idUtilisateur = ?, idTrajetTrain = ?, idTrajetMetro = ?, idTrajetBus = ?, idScooter = ?, idTaxi = ?, idParking = ? WHERE idReservation = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Timestamp timestamp = Timestamp.valueOf(obj.getDateReservation());
            pstmt.setTimestamp(1, timestamp);
            pstmt.setInt(2, obj.getIdUtilisateur());

            // Pour les champs qui peuvent être null, on utilise setObject
            pstmt.setObject(3, obj.getIdTrajetTrain(), Types.INTEGER);  // idTrajetTrain peut être null
            pstmt.setObject(4, obj.getIdTrajetMetro(), Types.INTEGER);  // idTrajetMetro peut être null
            pstmt.setObject(5, obj.getIdTrajetBus(), Types.INTEGER);    // idTrajetBus peut être null
            pstmt.setObject(6, obj.getIdScooter(), Types.INTEGER);      // idScooter peut être null
            pstmt.setObject(7, obj.getIdTaxi(), Types.INTEGER);         // idTaxi peut être null
            pstmt.setObject(8, obj.getIdParking(), Types.INTEGER);      // idParking peut être null

            pstmt.setInt(9, obj.getIdReservation());
            pstmt.executeUpdate();
        }
    }


    @Override
    public void delete(Reservation obj) throws SQLException {
        String sql = "DELETE FROM reservation WHERE idReservation = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, obj.getIdReservation());
            pstmt.executeUpdate();
        }
    }

    @Override
    public List<Reservation> getAll() throws SQLException {
        String sql = "SELECT * FROM reservation";
        List<Reservation> reservations = new ArrayList<>();
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setIdReservation(rs.getInt("idReservation"));
                Timestamp timestamp = rs.getTimestamp("dateReservation");
                LocalDateTime dateReservation = timestamp.toLocalDateTime();
                reservation.setDateReservation(dateReservation);
                reservation.setIdUtilisateur(rs.getInt("idUtilisateur"));
                reservation.setIdTrajetTrain(rs.getInt("idTrajetTrain"));
                reservation.setIdTrajetMetro(rs.getInt("idTrajetMetro"));
                reservation.setIdTrajetBus(rs.getInt("idTrajetBus"));
                reservation.setIdScooter(rs.getInt("idScooter"));
                reservation.setIdTaxi(rs.getInt("idTaxi"));
                reservation.setIdParking(rs.getInt("idParking"));
                reservations.add(reservation);
            }
        }
        return reservations;
    }
public  String getUser(int id) throws  SQLException{
    String sql = "SELECT * FROM utilisateur WHERE idUtilisateur = ?";
    String s="";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                s=rs.getString("nomUtilisateur");
            }
        }
    }
    return s;
    }

    public Reservation getById(int id) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE idReservation = ?";
        Reservation reservation = null;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    reservation = new Reservation();
                    reservation.setIdReservation(rs.getInt("idReservation"));
                    Timestamp timestamp = rs.getTimestamp("dateReservation");
                    LocalDateTime dateReservation = timestamp.toLocalDateTime();
                    reservation.setDateReservation(dateReservation);
                    reservation.setIdUtilisateur(rs.getInt("idUtilisateur"));
                    reservation.setIdTrajetTrain(rs.getInt("idTrajetTrain"));
                    reservation.setIdTrajetMetro(rs.getInt("idTrajetMetro"));
                    reservation.setIdTrajetBus(rs.getInt("idTrajetBus"));
                    reservation.setIdScooter(rs.getInt("idScooter"));
                    reservation.setIdTaxi(rs.getInt("idTaxi"));
                    reservation.setIdParking(rs.getInt("idParking"));
                }
            }
        }
        return reservation;
    }

    public boolean isReservationExists(int idReservation) throws SQLException {
        String sql = "SELECT idReservation FROM reservation WHERE idReservation = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idReservation);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
