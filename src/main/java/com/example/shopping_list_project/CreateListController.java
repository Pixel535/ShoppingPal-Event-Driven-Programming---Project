package com.example.shopping_list_project;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateListController {

    @FXML
    private Button AddListName;

    @FXML
    private Button AddToListButton;

    @FXML
    private Button FinishButton;

    @FXML
    private Button GoBackToSearchingButton;

    @FXML
    private Button MyListsButton;

    @FXML
    private Label NameLabel;

    @FXML
    private Label NameLabel2;

    @FXML
    private TextField NameTextBox;

    @FXML
    private TextField NameTextField;

    @FXML
    private Button NotFoundButton;

    @FXML
    private Label PriceLabel;

    @FXML
    private Label PriceLabel1;

    @FXML
    private TextField PriceTextBox;

    @FXML
    private TextField PriceTextField1;

    @FXML
    private TextField QuantityEnter;

    @FXML
    private Label QuantityLabel;

    @FXML
    private Label QuantityLabel2;

    @FXML
    private TextField QuantityTextBox;

    @FXML
    private TextField SearchBar;

    @FXML
    private Label SearchLabel;

    @FXML
    private ListView<String> SearchedProducts;

    @FXML
    private Label error1;

    @FXML
    private Label error2;

    @FXML
    private Label error3;

    @FXML
    private Label error4;

    @FXML
    private Label error5;

    @FXML
    private Label error6;

    String ListName;
    String ProductName;
    int quantity;
    double price;
    private ListFunctions ListFunctions;
    private MultithreadingProductSearch productSearch = new MultithreadingProductSearch();

    @FXML
    void FinishAdding(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyLists-view.fxml"));
        Parent root = loader.load();
        MyListsController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        ConfigPropertiesReader.ReadPropertiesFile();
        productSearch.shutdown();
    }

    @FXML
    void AddToList(MouseEvent event) {

        ListFunctions = new ListFunctions();
        if(NotFoundButton.isVisible())
        {
            ProductName = SearchedProducts.getSelectionModel().getSelectedItem();
            try {
                quantity = Integer.parseInt(QuantityEnter.getText());
            }
            catch (Exception e)
            {
                error2.setVisible(true);
            }
            try {
                price = Double.parseDouble(PriceTextField1.getText());
            }
            catch (Exception e)
            {
                error6.setVisible(true);
            }

            if(!error2.isVisible() && !ProductName.trim().isEmpty())
            {
                price = quantity * price;
                Product product = new Product(ProductName, quantity, price);
                ListFunctions.addProduct(ListName, product);
            }
        }
        else if(GoBackToSearchingButton.isVisible())
        {
            ProductName = NameTextBox.getText();
            if(ProductName.trim().isEmpty())
            {
                error3.setVisible(true);
            }
            try {
                price = Double.parseDouble(PriceTextBox.getText());
            }
            catch (Exception e)
            {
                error4.setVisible(true);
            }
            try {
                quantity = Integer.parseInt(QuantityTextBox.getText());
            }
            catch (Exception e)
            {
                error5.setVisible(true);
            }

            if(!error3.isVisible() && !error4.isVisible() && !error5.isVisible())
            {
                price = quantity * price;
                Product product = new Product(ProductName, quantity, price);
                ListFunctions.addProduct(ListName, product);
            }

        }

        NameTextBox.clear();
        PriceTextBox.clear();
        QuantityTextBox.clear();
        QuantityEnter.clear();
        SearchBar.clear();
        PriceTextField1.clear();
    }

    @FXML
    void AddedListName(MouseEvent event) {

        if(ListName == null)
        {
            ListName = NameTextField.getText();
        }

        if(ListName.trim().isEmpty())
        {
            error1.setVisible(true);
        }
        else
        {
            error1.setVisible(false);
            NameLabel.setVisible(false);
            AddListName.setVisible(false);
            NameTextField.setVisible(false);
            MyListsButton.setVisible(false);

            SearchBar.clear();
            QuantityEnter.clear();
            PriceTextField1.clear();

            FinishButton.setVisible(true);
            SearchLabel.setVisible(true);
            SearchBar.setVisible(true);
            SearchedProducts.setVisible(true);
            NotFoundButton.setVisible(true);
            QuantityLabel.setVisible(true);
            QuantityEnter.setVisible(true);
            AddToListButton.setVisible(true);
            PriceTextField1.setVisible(true);
            PriceLabel1.setVisible(true);

            SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
                ObservableList<String> productNames = productSearch.searchProductsMulti(newValue);
                SearchedProducts.setItems(productNames);
            });

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    productSearch.shutdown();
                }
            });

        }
    }

    @FXML
    void GoBackToSearching(MouseEvent event) throws IOException {

        ConfigPropertiesReader.ReadPropertiesFile();

        error3.setVisible(false);
        error4.setVisible(false);
        error5.setVisible(false);
        AddToListButton.setVisible(false);
        GoBackToSearchingButton.setVisible(false);
        NameLabel2.setVisible(false);
        PriceLabel.setVisible(false);
        QuantityLabel2.setVisible(false);
        NameTextBox.setVisible(false);
        PriceTextBox.setVisible(false);
        QuantityTextBox.setVisible(false);

        SearchBar.clear();
        QuantityEnter.clear();
        PriceTextField1.clear();

        FinishButton.setVisible(true);
        SearchLabel.setVisible(true);
        SearchBar.setVisible(true);
        SearchedProducts.setVisible(true);
        NotFoundButton.setVisible(true);
        QuantityLabel.setVisible(true);
        QuantityEnter.setVisible(true);
        AddToListButton.setVisible(true);
        PriceTextField1.setVisible(true);
        PriceLabel1.setVisible(true);
    }

    @FXML
    void ProductNotFound(MouseEvent event) throws IOException {

        ConfigPropertiesReader.ReadPropertiesFile();

        error2.setVisible(false);
        error6.setVisible(false);
        FinishButton.setVisible(false);
        SearchLabel.setVisible(false);
        SearchBar.setVisible(false);
        SearchedProducts.setVisible(false);
        NotFoundButton.setVisible(false);
        QuantityLabel.setVisible(false);
        QuantityEnter.setVisible(false);
        PriceTextField1.setVisible(false);
        PriceLabel1.setVisible(false);

        NameTextBox.clear();
        PriceTextBox.clear();
        QuantityTextBox.clear();

        AddToListButton.setVisible(true);
        GoBackToSearchingButton.setVisible(true);
        NameLabel2.setVisible(true);
        PriceLabel.setVisible(true);
        QuantityLabel2.setVisible(true);
        NameTextBox.setVisible(true);
        PriceTextBox.setVisible(true);
        QuantityTextBox.setVisible(true);
        FinishButton.setVisible(true);
    }

    @FXML
    void SwitchToMyListsScene(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyLists-view.fxml"));
        Parent root = loader.load();
        MyListsController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        ConfigPropertiesReader.ReadPropertiesFile();
        productSearch.shutdown();
    }

    void setListName (String listName) {
        ListName = listName;
    }

}
