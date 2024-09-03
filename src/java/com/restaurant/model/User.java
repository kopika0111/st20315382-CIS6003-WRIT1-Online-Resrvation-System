/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.restaurant.model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author hp
 */
public class User {
    private int userID;
    private String username;
    private String password;
    private String email;
    private int roleID;
    private String name;
    private String contactInfo;
    private Date registrationDate;
    private String fileName;
    // Getters and setters

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getRoleID() {
        return roleID;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getUserID() {
        return userID;
    }

    public void setImagePath(String fileName) {
        this.fileName = fileName;
    }

    public String getImagePath() {
        return fileName;
    }
}
