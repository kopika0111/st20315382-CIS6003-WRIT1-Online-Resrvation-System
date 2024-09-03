/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restaurant.dao;

/**
 *
 * @author hp
 */
import com.restaurant.model.User;
import com.restaurant.observer.Subject;
import com.restaurant.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends Subject{
    private Connection connection;
    
    public UserDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }
    public void addUser(User user) throws SQLException {
        String query = "INSERT INTO users (Username, Password, Email, RoleID, Name, ContactInfo, Image) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (//Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setInt(4, user.getRoleID());
            pstmt.setString(5, user.getName());
            pstmt.setString(6, user.getContactInfo());
            pstmt.setString(7, user.getImagePath());
            pstmt.executeUpdate();
        }
    }

    public User getUserByUsername(String username) throws SQLException {
        String query = "SELECT * FROM users WHERE Username = ?";
//        System.out.println("count users dao:"+ query);
        try (//Connection conn = DatabaseConnection.getConnection();         
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                user.setRoleID(rs.getInt("RoleID"));
                user.setName(rs.getString("Name"));
                user.setContactInfo(rs.getString("ContactInfo"));
                user.setRegistrationDate(rs.getTimestamp("RegistrationDate"));
                user.setImagePath(rs.getString("Image"));
                
                return user;
            }
        }
        return null;
    }

    public void updateUser(User user) throws SQLException {
        String sql = "UPDATE users SET Username = ?, Name = ?, Email = ?,  Password = ?, ContactInfo = ?, Image = ?, RoleID = ?  WHERE UserID = ?";
        try (//Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {            
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getContactInfo());
            statement.setString(6, user.getImagePath());
            statement.setInt(7, user.getRoleID());
            statement.setInt(8, user.getUserID());
            statement.executeUpdate();

            // Notify observers after updating the user
            notifyObservers(user);
        }
    }
    // Other CRUD methods

    public int getUserCount() throws SQLException {
        String query = "SELECT COUNT(*) AS userCount FROM users";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userCount");
            }
        }
        return 0;
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                user.setRoleID(rs.getInt("RoleID"));
                user.setName(rs.getString("Name"));
                user.setContactInfo(rs.getString("ContactInfo"));
                user.setImagePath(rs.getString("Image"));
                users.add(user);
            }
        }
        return users;
    }
    
    public List<User> getAllCustomers() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE RoleID = 3";
        try (PreparedStatement pstmt = connection.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                user.setRoleID(rs.getInt("RoleID"));
                user.setName(rs.getString("Name"));
                user.setContactInfo(rs.getString("ContactInfo"));
                user.setImagePath(rs.getString("Image"));
                users.add(user);
            }
        }
        return users;
    }

     public User getUserById(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("UserID"));
                user.setUsername(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                user.setRoleID(rs.getInt("RoleID"));
                user.setName(rs.getString("Name"));
                user.setContactInfo(rs.getString("ContactInfo"));
                user.setImagePath(rs.getString("Image"));
                return user;
            }
        }
        return null;
    }

    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE UserID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

}


