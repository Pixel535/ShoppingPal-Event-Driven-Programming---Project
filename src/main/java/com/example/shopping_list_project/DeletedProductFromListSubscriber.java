package com.example.shopping_list_project;

import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class DeletedProductFromListSubscriber {
    private final ListView<String> ListOfProducts;
    private double totalPrice;
    private Label PriceLabel;
    private Label CompletedListText;

    public DeletedProductFromListSubscriber(ListView<String> ListOfProducts, double totalPrice, Label PriceLabel, Label CompletedListText) {
        this.ListOfProducts = ListOfProducts;
        this.totalPrice = totalPrice;
        this.PriceLabel = PriceLabel;
        this.CompletedListText = CompletedListText;
    }

    @Subscribe
    public void handleProductDeletedEvent(DeletedProductEvent event) {
        Platform.runLater(() -> {
            String productName = event.getProductName();
            double price = event.getPrice();
            int quantity = event.getQuantity();
            totalPrice = totalPrice - price;
            String ProductDetails = quantity + " | " + productName + " | " + price + " zl";
            ListOfProducts.getItems().remove(ProductDetails);
            PriceLabel.setText(String.valueOf(totalPrice) + " zl");

            if(ListOfProducts.getItems().isEmpty())
            {
                ListOfProducts.setVisible(false);
                CompletedListText.setVisible(true);
            }
        });
    }
}
