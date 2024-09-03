package com.restaurant.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/restaurant";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static final Logger LOGGER = Logger.getLogger(DatabaseConnection.class.getName());

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "MySQL JDBC Driver not found.", e);
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Connection to the database failed.", e);
            throw new RuntimeException("Connection to the database failed.", e);
        }
    }
}
