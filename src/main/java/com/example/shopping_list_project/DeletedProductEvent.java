package com.example.shopping_list_project;

public class DeletedProductEvent {
    private String listName;
    private String productName;
    private double price;
    private int quantity;

    public DeletedProductEvent(String listName, String productName, double price, int quantity) {
        this.listName = listName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getListName() {
        return listName;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
