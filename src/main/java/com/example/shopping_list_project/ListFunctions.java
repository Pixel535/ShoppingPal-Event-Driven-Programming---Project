package com.example.shopping_list_project;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import java.util.concurrent.CompletableFuture;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import com.google.common.eventbus.EventBus;


import java.util.ArrayList;
import java.util.List;

public class ListFunctions {
    public static String COLLECTION_NAME;
    public static String productName_key;
    public static String quantity_key;
    public static String price_key;
    public static String nazwaListy_key;
    public static String ShoppingList_key;
    public static String listName_key;
    private MongoCollection<Document> collection;
    private static EventBus eventBus = new EventBus();

    public ListFunctions() {
        DataBaseConnector.connect();
        MongoDatabase database = DataBaseConnector.getDatabase();
        collection = database.getCollection(COLLECTION_NAME);
    }

    public CompletableFuture<Void> addProduct(String listName, Product product) {
        Document listItem = new Document()
                .append(productName_key, product.getName())
                .append(quantity_key, product.getQuantity())
                .append(price_key, product.getPrice());
        Document query = new Document().append(nazwaListy_key, listName);
        Document update = new Document().append("$push", new Document().append(ShoppingList_key, listItem));

        return CompletableFuture.runAsync(() -> {
            FindOneAndUpdateOptions options = new FindOneAndUpdateOptions().upsert(true);
            collection.findOneAndUpdate(query, update, options);
        });
    }

    public CompletableFuture<List<ShoppingList>> getAllShoppingLists() {
        return CompletableFuture.supplyAsync(() -> {
            List<ShoppingList> shoppingLists = new ArrayList<>();
            try (MongoCursor<Document> cursor = collection.find().iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    String listName = document.getString(nazwaListy_key);
                    List<Document> shoppingList = document.getList(ShoppingList_key, Document.class);
                    List<Product> productList = new ArrayList<>();
                    for(Document item : shoppingList)
                    {
                        String productName = item.getString(productName_key);
                        int quantity = item.getInteger(quantity_key);
                        double price = item.getDouble(price_key);
                        Product product = new Product(productName, quantity, price);
                        productList.add(product);
                    }
                    ShoppingList shoppingListObj = new ShoppingList(listName, productList);
                    shoppingLists.add(shoppingListObj);
                }
            }
            return shoppingLists;
        });
    }

    public CompletableFuture<Void> deleteProduct(String listName, String productName, double price, int quantity) {
        Document filter = new Document()
                .append(nazwaListy_key, listName)
                .append("shopping_list.product_name", productName);
        Document update = new Document()
                .append("$pull", new Document(ShoppingList_key, new Document(productName_key, productName)));
        return CompletableFuture.runAsync(() -> {
            collection.updateOne(filter, update);
            eventBus.post(new DeletedProductEvent(listName, productName, price, quantity));
        });
    }

    public CompletableFuture<List<ShoppingList>> findShoppingListByName(String GivenListName) {
        return CompletableFuture.supplyAsync(() -> {
            List<ShoppingList> shoppingLists = new ArrayList<>();
            Bson filter = Filters.regex(nazwaListy_key, ".*" + GivenListName + ".*", "i");
            try (MongoCursor<Document> cursor = collection.find(filter).iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    String listName = document.getString(nazwaListy_key);
                    List<Document> shoppingList = document.getList(ShoppingList_key, Document.class);
                    List<Product> productList = new ArrayList<>();
                    for (Document item : shoppingList) {
                        String productName = item.getString(productName_key);
                        int quantity = item.getInteger(quantity_key);
                        double price = item.getDouble(price_key);
                        Product product = new Product(productName, quantity, price);
                        productList.add(product);
                    }
                    ShoppingList shoppingListObj = new ShoppingList(listName, productList);
                    shoppingLists.add(shoppingListObj);
                }
            }
            return shoppingLists;
        });
    }

    public CompletableFuture<Long> deleteShoppingListByName(String listName) {
        return CompletableFuture.supplyAsync(() -> {
            Bson filter = Filters.eq(nazwaListy_key, listName);
            DeleteResult result = collection.deleteOne(filter);
            if (result.getDeletedCount() > 0) {
                eventBus.post(new DeletedListEvent(listName));
            }
            return result.getDeletedCount();
        });
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
