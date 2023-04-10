package com.example.shopping_list_project;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MultithreadingProductSearch {

    private final ExecutorService executorService;
    private final OpenFoodFactsAPISearch OpenFoodFactsAPISearch;


    public MultithreadingProductSearch() {
        this.executorService = Executors.newFixedThreadPool(1);
        this.OpenFoodFactsAPISearch = new OpenFoodFactsAPISearch();
    }

    public ObservableList<String> searchProductsMulti(String GivenInput) {
        ObservableList<String> productNames = FXCollections.observableArrayList();
        if (GivenInput == null || GivenInput.isEmpty()) {
            return productNames;
        }

        executorService.submit(() -> {
            List<Product> searchResults = OpenFoodFactsAPISearch.SearchForProduct(GivenInput);
            List<String> names = searchResults.stream().map(Product::getName).toList();
            Platform.runLater(() -> {
                productNames.addAll(names);
            });
        });

        return productNames;
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
