package com.restaurant.dao;

import com.restaurant.model.Order;
import com.restaurant.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private Connection connection;

    public OrderDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public int addOrder(Order order) throws SQLException {
        String query = "INSERT INTO orders (CustomerID, OrderType, TableID, Total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, order.getCustomerID());
            pstmt.setString(2, order.getOrderType());
            pstmt.setInt(3, order.getTableID());
            pstmt.setDouble(4, order.getTotal());
            pstmt.executeUpdate();
            
            String idQuery = "SELECT LAST_INSERT_ID()";
            try (PreparedStatement idStmt = connection.prepareStatement(idQuery);
                 ResultSet rs = idStmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Return the last inserted OrderID
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new SQLException("Error while inserting order: " + e.getMessage(), e);
        }
    }

    public void updateOrder(Order order) throws SQLException {
        String query = "UPDATE orders SET CustomerID = ?, OrderType = ?, TableID = ?, Total = ? WHERE ID = ? "; 
        System.out.println("last insert id in hendle dao: " + order.getID());
        try (PreparedStatement pstmt = connection.prepareStatement(query)) { 
            pstmt.setInt(1, order.getCustomerID());
            pstmt.setString(2, order.getOrderType()); 
            pstmt.setInt(3, order.getTableID()); 
            pstmt.setDouble(4, order.getTotal()); 
            pstmt.setInt(5, order.getID()); 
            pstmt.executeUpdate(); 
        } 
    }
    
    public void deleteOrder(int orderID) throws SQLException {
        String query = "DELETE FROM orders WHERE ID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, orderID);
            pstmt.executeUpdate();
        }
    }

    public Order getOrderById(int orderID) throws SQLException {
        String query = "SELECT * FROM orders WHERE ID = ?";
        Order order = null;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, orderID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                order = new Order();
                order.setID(rs.getInt("ID"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setOrderType(rs.getString("OrderType"));
                order.setTableID(rs.getInt("TableID"));
                order.setTotal(rs.getDouble("Total"));
            }
        }
        return order;
    }

    public List<Order> getAllOrders() throws SQLException {
        String query = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setID(rs.getInt("ID"));
                order.setCustomerID(rs.getInt("CustomerID"));
                order.setOrderType(rs.getString("OrderType"));
                order.setTableID(rs.getInt("TableID"));
                order.setTotal(rs.getDouble("Total"));
                orders.add(order);
            }
        }
        return orders;
    }

    public int getOrderCount() throws SQLException {
        String query = "SELECT COUNT(*) AS orderCount FROM orders";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("orderCount");
            }
        }
        return 0;
    }
}
