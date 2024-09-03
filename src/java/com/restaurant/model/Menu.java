package com.restaurant.model;

public class Menu {
    private int menuID;
    private String name;
    private String category;
    private String description;
    private double price;
    private String imagePath;

    // Getters and Setters
    public int getMenuID() { 
        return menuID; 
    }
    
    public void setMenuID(int menuID) { 
        this.menuID = menuID; 
    }

    public String getName() {
        return name; 
    }
    public void setName(String name) {
        this.name = name; 
    }

    public String getCategory() {
        return category; 
    }
    
    public void setCategory(String category) { 
        this.category = category; 
    }

    public String getDescription() {
        return description; 
    }
    
    public void setDescription(String description) {
        this.description = description; 
    }

    public double getPrice() { 
        return price; 
    }
    
    public void setPrice(double price) {
        this.price = price; 
    }

    public String getImagePath() { 
        return imagePath; 
    }
    
    public void setImagePath(String imagePath) { 
        this.imagePath = imagePath; 
    }
}
