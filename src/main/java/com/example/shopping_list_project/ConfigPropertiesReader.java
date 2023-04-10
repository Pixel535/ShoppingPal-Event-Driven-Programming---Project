package com.example.shopping_list_project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesReader {
    private static Properties prop = new Properties();

    public static void ReadPropertiesFile() throws IOException {

        InputStream input = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\application.properties");
        prop.load(input);

        ShoppingPal.HomeView = prop.getProperty("sbv.home");
        ShoppingPal.AppTitle = prop.getProperty("app.window.title");

        OpenFoodFactsAPISearch.APIURL = prop.getProperty("api.url");
        OpenFoodFactsAPISearch.APISearchFilter = prop.getProperty("api.search");
        OpenFoodFactsAPISearch.product_key = prop.getProperty("api.json.array.product.key");
        OpenFoodFactsAPISearch.productName_key = prop.getProperty("api.json.array.product_name.key");
        OpenFoodFactsAPISearch.price_key = prop.getProperty("api.json.array.price.key");

        MyListsController.CreateListView = prop.getProperty("sbv.create_list");
        MyListsController.HomeView = prop.getProperty("sbv.home");
        MyListsController.ListView = prop.getProperty("sbv.list");

        ListFunctions.COLLECTION_NAME = prop.getProperty("spring.data.mongodb.collection.name");
        ListFunctions.productName_key = prop.getProperty("api.json.array.product_name.key");
        ListFunctions.quantity_key = prop.getProperty("api.json.array.quantity");
        ListFunctions.price_key = prop.getProperty("api.json.array.price.key");
        ListFunctions.bought_key = prop.getProperty("api.json.array.bought");
        ListFunctions.nazwaListy_key = prop.getProperty("api.json.array.nazwa_listy");
        ListFunctions.ShoppingList_key = prop.getProperty("api.json.array.shopping_list");
        ListFunctions.listName_key = prop.getProperty("api.json.array.listName_list");

        ListController.HomeView = prop.getProperty("sbv.home");
        ListController.MyListsView = prop.getProperty("sbv.myLists");
        ListController.CreateListView = prop.getProperty("sbv.create_list");

        HomeController.CreateListView = prop.getProperty("sbv.create_list");
        HomeController.ListView = prop.getProperty("sbv.list");
        HomeController.HomeView = prop.getProperty("sbv.home");
        HomeController.MyListsView = prop.getProperty("sbv.myLists");

        DataBaseConnector.urlString = prop.getProperty("spring.data.mongodb.host");
        DataBaseConnector.dataBaseName = prop.getProperty("spring.data.mongodb.dataBase.name");
        DataBaseConnector.port = prop.getProperty("spring.data.mongodb.port");
    }

}
