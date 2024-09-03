package com.restaurant.model;

public class Table {
    private int id;
    private String status;
    private int capacity;

    // Getters and Setters
    // ...

    public int getTableID() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }
    
    public void setTableID(int id) {
        this.id = id;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
