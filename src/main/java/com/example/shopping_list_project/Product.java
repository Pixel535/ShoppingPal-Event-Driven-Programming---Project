package com.example.shopping_list_project;

public class Product {
    private String name;
    private int quantity;
    private boolean isPurchased;
    private double price;

    public Product(String name, int quantity, boolean isPurchased, double price) {
        this.name = name;
        this.quantity = quantity;
        this.isPurchased = isPurchased;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isPurchased() {
        return isPurchased;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
