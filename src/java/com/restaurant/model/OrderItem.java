package com.restaurant.model;

public class OrderItem {
    private int orderID;
    private int itemID;
    private int qty;

    // Getters and Setters
    public int getOrderID() { 
        return orderID; 
    }
    
    public void setOrderID(int orderID) { 
        this.orderID = orderID; 
    }

    public int getItemID() { 
        return itemID; 
    }
    
    public void setItemID(int itemID) { 
        this.itemID = itemID; 
    }

    public int getQty() {
        return qty; 
    }
    
    public void setQty(int qty) {
        this.qty = qty; 
    }
}
