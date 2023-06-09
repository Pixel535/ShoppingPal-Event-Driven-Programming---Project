package com.example.shopping_list_project.ListsAndProducts;

import com.example.shopping_list_project.ListsAndProducts.Product;

import java.util.List;

public class ShoppingList {
    private String name;
    private List<Product> products;

    public ShoppingList(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
