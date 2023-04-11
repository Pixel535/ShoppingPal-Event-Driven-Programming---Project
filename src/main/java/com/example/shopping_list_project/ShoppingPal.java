package com.example.shopping_list_project;

import com.example.shopping_list_project.Config.ConfigPropertiesReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ShoppingPal extends Application {

    public static String HomeView;
    public static String AppTitle;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(ShoppingPal.class.getResource(HomeView));

        Image icon = new Image(System.getProperty("user.dir") + "\\src\\img\\ShoppingCart.png");

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle(AppTitle);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws IOException {

        ConfigPropertiesReader.ReadPropertiesFile();
        launch();

    }
}