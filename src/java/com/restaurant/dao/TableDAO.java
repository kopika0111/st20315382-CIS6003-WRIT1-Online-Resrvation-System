/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restaurant.dao;

import com.restaurant.model.Table;
import com.restaurant.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dell
 */
public class TableDAO {
    private Connection connection;

    public TableDAO() {
        this.connection = DatabaseConnection.getConnection();
    }
    
    public List<Table> getAllTables() throws SQLException {
        String sql = "SELECT * FROM tables";
        List<Table> tables = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Table table = new Table();
                table.setTableID(rs.getInt("id"));
                table.setCapacity(rs.getInt("capacity"));
                table.setStatus(rs.getString("Status"));
                tables.add(table);
            }
        }
        return tables;
    }

    public void addTable(Table table) throws SQLException {
        String query = "INSERT INTO tables (capacity, status) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, table.getCapacity());
            pstmt.setString(2, table.getStatus());
            pstmt.executeUpdate();
        }
    }

    public void updateTable(Table table) throws SQLException {
        String query = "UPDATE tables SET capacity = ?, status = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, table.getCapacity());
            pstmt.setString(2, table.getStatus());
            pstmt.setInt(3, table.getTableID());
            pstmt.executeUpdate();
        }
    }

    public void deleteTable(int tableID) throws SQLException {
        String query = "DELETE FROM tables WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, tableID);
            pstmt.executeUpdate();
        }
    }
    
}
