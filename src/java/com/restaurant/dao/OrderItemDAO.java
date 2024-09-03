package com.restaurant.dao;

import com.restaurant.model.OrderItem;
import com.restaurant.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    private Connection connection;

    public OrderItemDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public void addOrderItem(OrderItem orderItem) throws SQLException {
        String query = "INSERT INTO order_items (OrderID, ItemID, Qty) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, orderItem.getOrderID());
            pstmt.setInt(2, orderItem.getItemID());
            pstmt.setInt(3, orderItem.getQty());
            pstmt.executeUpdate();
        }
    }

    public void updateOrderItem(OrderItem orderItem) throws SQLException {
        String query = "UPDATE order_items SET ItemID = ?, Qty = ? WHERE OrderID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, orderItem.getItemID());
            pstmt.setInt(2, orderItem.getQty());
            pstmt.setInt(3, orderItem.getOrderID());
            pstmt.executeUpdate();
        }
    }

    public void deleteOrderItem(int orderID, int itemID) throws SQLException {
        String query = "DELETE FROM order_items WHERE OrderID = ? AND ItemID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, orderID);
            pstmt.setInt(2, itemID);
            pstmt.executeUpdate();
        }
    }

    public void deleteOrderItemsByOrderID(int orderID) throws SQLException {
        String query = "DELETE FROM order_items WHERE OrderID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, orderID);
            pstmt.executeUpdate();
        }
    }

    public List<OrderItem> getOrderItemsByOrderID(int orderID) throws SQLException {
        String query = "SELECT * FROM order_items WHERE OrderID = ?";
        List<OrderItem> orderItems = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, orderID);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderID(rs.getInt("OrderID"));
                orderItem.setItemID(rs.getInt("ItemID"));
                orderItem.setQty(rs.getInt("Qty"));
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }
}
