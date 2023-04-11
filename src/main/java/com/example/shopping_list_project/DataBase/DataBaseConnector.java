package com.example.shopping_list_project.DataBase;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DataBaseConnector {
    private static MongoClient mongoClient;
    private static MongoDatabase database;

    public static String urlString;
    public static String port;
    public static String dataBaseName;

    public static void connect() {
        urlString = "mongodb://" + urlString + ":" + port;
        MongoClientURI uri = new MongoClientURI(urlString);
        mongoClient = new MongoClient(uri);
        database = mongoClient.getDatabase(dataBaseName);
    }

    public static MongoDatabase getDatabase() {
        return database;
    }

    public static void close() {
        mongoClient.close();
    }
}
