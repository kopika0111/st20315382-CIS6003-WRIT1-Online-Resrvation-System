package com.restaurant.dao;

import com.restaurant.model.Menu;
import com.restaurant.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    private Connection connection;

    public MenuDAO() {
        this.connection = DatabaseConnection.getConnection();
    }

    public List<Menu> getAllMenus() throws SQLException {
        List<Menu> menus = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setMenuID(rs.getInt("ProductID"));
                menu.setName(rs.getString("Name"));
                menu.setCategory(rs.getString("Category"));
                menu.setDescription(rs.getString("Description"));
                menu.setPrice(rs.getDouble("Price"));
                menu.setImagePath(rs.getString("Image"));
                menus.add(menu);
            }
        }
        return menus;
    }

    public void addMenu(Menu menu) throws SQLException {
        String query = "INSERT INTO products (Name, Category, Description, Price, Image) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, menu.getName());
            pstmt.setString(2, menu.getCategory());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setString(5, menu.getImagePath());
            pstmt.executeUpdate();
        }
    }

    public void updateMenu(Menu menu) throws SQLException {
        String query = "UPDATE products SET Name = ?, Category = ?, Description = ?, Price = ?, Image = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, menu.getName());
            pstmt.setString(2, menu.getCategory());
            pstmt.setString(3, menu.getDescription());
            pstmt.setDouble(4, menu.getPrice());
            pstmt.setString(5, menu.getImagePath());
            pstmt.setInt(6, menu.getMenuID());
            pstmt.executeUpdate();
        }
    }

    public void deleteMenu(int menuID) throws SQLException {
        String query = "DELETE FROM products WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, menuID);
            pstmt.executeUpdate();
        }
    }

    public Menu getMenuById(int menuID) throws SQLException {
        Menu menu = null;
        String query = "SELECT * FROM products WHERE ProductID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, menuID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                menu = new Menu();
                menu.setMenuID(rs.getInt("ProductID"));
                menu.setName(rs.getString("Name"));
                menu.setCategory(rs.getString("Category"));
                menu.setDescription(rs.getString("Description"));
                menu.setPrice(rs.getDouble("Price"));
                menu.setImagePath(rs.getString("Image"));
            }
        }
        return menu;
    }

    public List<Menu> getAllItems() throws SQLException {
        String sql = "SELECT * FROM products";
        List<Menu> menus = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Menu menu = new Menu();
                menu.setMenuID(rs.getInt("ProductID"));
                menu.setName(rs.getString("Name"));
                menu.setCategory(rs.getString("Category"));
                menu.setDescription(rs.getString("Description"));
                menu.setPrice(rs.getDouble("Price"));
                menu.setImagePath(rs.getString("Image"));
                menus.add(menu);
            }
        }
        return menus;
    }
}
