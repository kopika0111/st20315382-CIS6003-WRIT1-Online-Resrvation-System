package com.restaurant.model;

public class Order {
    private int ID;
    private int customerID;
    private String orderType;
    private int tableID;
    private double total;

    // Getters and Setters
    public int getID() { 
        return ID; 
    }
    
    public void setID(int ID) {
        this.ID = ID; 
    }

    public int getCustomerID() { 
        return customerID; 
    }
    
    public void setCustomerID(int customerID) { 
        this.customerID = customerID;
    }

    public String getOrderType() {
        return orderType; 
    }
    
    public void setOrderType(String orderType) { 
        this.orderType = orderType; 
    }

    public int getTableID() { 
        return tableID; 
    }
    
    public void setTableID(int tableID) { 
        this.tableID = tableID;
    }

    public double getTotal() {
        return total; 
    }
    
    public void setTotal(double total) { 
        this.total = total;
    }
}
