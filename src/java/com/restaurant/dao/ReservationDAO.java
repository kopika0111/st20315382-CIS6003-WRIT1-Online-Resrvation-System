package com.restaurant.dao;

import com.restaurant.model.Reservation;
import com.restaurant.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void createReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (CustomerID, StaffID, ReservationType, ReservationTime, NumberOfGuests, Status, PaymentStatus) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reservation.getCustomerID());
            stmt.setObject(2, reservation.getStaffID());
            stmt.setString(3, reservation.getReservationType());
            stmt.setString(4, reservation.getReservationTime());
            stmt.setInt(5, 1);
            stmt.setString(6, reservation.getStatus());
            stmt.setString(7, reservation.getPaymentStatus());
            stmt.executeUpdate();
        }
    }

    public List<Reservation> getReservationsForCustomer(int customerID) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE CustomerID = ?";
        List<Reservation> reservations = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(rs.getInt("ReservationID"));
                reservation.setCustomerID(rs.getInt("CustomerID"));
                reservation.setStaffID((Integer) rs.getObject("StaffID"));
                reservation.setReservationType(rs.getString("ReservationType"));
                reservation.setReservationTime(rs.getString("ReservationTime"));
//                reservation.setNumberOfGuests(rs.getInt("NumberOfGuests"));
                reservation.setStatus(rs.getString("Status"));
                reservation.setPaymentStatus(rs.getString("PaymentStatus"));
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public void deleteReservation(int reservationID) throws SQLException {
        String query = "DELETE FROM reservations WHERE reservationID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, reservationID);
            pstmt.executeUpdate();
        }
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        String query = "UPDATE reservations SET ReservationType = ?, ReservationTime = ?, status = ?, PaymentStatus = ?, TableID = ? WHERE ReservationID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, reservation.getReservationType());
            pstmt.setString(2, reservation.getReservationTime());
            pstmt.setString(3, reservation.getStatus());
            pstmt.setString(4, reservation.getPaymentStatus());
            pstmt.setInt(5, reservation.getTableID());
            pstmt.setInt(6, reservation.getReservationID());
            pstmt.executeUpdate();
        }
    }

    public Reservation getReservationById(int reservationID) throws SQLException {
        Reservation res = null;
        String query = "SELECT r.*, t.id AS tableNumber, t.capacity FROM reservations r LEFT JOIN tables t ON r.TableID = t.id WHERE r.ReservationID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, reservationID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    res = new Reservation();
                    res.setReservationID(rs.getInt("reservationID"));
                    res.setCustomerID(rs.getInt("customerID"));
                    res.setReservationType(rs.getString("reservationType"));
                    res.setReservationTime(rs.getString("reservationTime"));
                    res.setStatus(rs.getString("status"));
                    res.setPaymentStatus(rs.getString("paymentStatus"));
                    res.setTableID(rs.getInt("TableID"));
                    res.setTableNumber(rs.getInt("tableNumber"));
                    res.setCapacity(rs.getInt("capacity"));
                }
            }
        }
        return res;
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.*, t.id AS tableNumber, t.capacity FROM reservations r LEFT JOIN tables t ON r.TableID = t.id";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Reservation res = new Reservation();
                res.setReservationID(rs.getInt("reservationID"));
                res.setCustomerID(rs.getInt("customerID"));
                res.setReservationType(rs.getString("reservationType"));
                res.setReservationTime(rs.getString("reservationTime"));
                res.setStatus(rs.getString("status"));
                res.setPaymentStatus(rs.getString("paymentStatus"));
                res.setTableID(rs.getInt("TableID"));
                res.setTableNumber(rs.getInt("tableNumber"));
                res.setCapacity(rs.getInt("capacity"));
                reservations.add(res);
            }
        }
        return reservations;
    }
    
    public List<Reservation> getReservationsByCustomerID(int customerID) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT r.*, t.id AS tableNumber, t.capacity FROM reservations r LEFT JOIN tables t ON r.TableID = t.id WHERE r.customerID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, customerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Reservation res = new Reservation();
                    res.setReservationID(rs.getInt("reservationID"));
                    res.setCustomerID(rs.getInt("customerID"));
                    res.setReservationType(rs.getString("reservationType"));
                    res.setReservationTime(rs.getString("reservationTime"));
                    res.setStatus(rs.getString("status"));
                    res.setPaymentStatus(rs.getString("paymentStatus"));
                    res.setTableID(rs.getInt("tableID"));
                    res.setTableNumber(rs.getInt("tableNumber"));
                    res.setCapacity(rs.getInt("capacity"));
                    reservations.add(res);
                }
            }
        }
        return reservations;
    }

    public int getReservationCount() throws SQLException {
        String query = "SELECT COUNT(*) AS userCount FROM reservations";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userCount");
            }
        }
        return 0;
    }
}


    
