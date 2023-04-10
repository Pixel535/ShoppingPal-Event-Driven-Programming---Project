package com.example.shopping_list_project;

import com.google.common.eventbus.EventBus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class ListController {

    @FXML
    private Button AddItemsButton;

    @FXML
    private Button HomeButton;

    @FXML
    private Label ListTitle;

    @FXML
    private Button MyListsButton;

    @FXML
    private Label PriceLabel;

    @FXML
    private Label TotalPriceText;

    @FXML
    private ListView<String> ProductsList;

    @FXML
    private Label CompletedListText;


    private ShoppingList shoppingList;
    public static String HomeView;
    public static String MyListsView;
    public static String CreateListView;
    private double totalPrice;
    private EventBus eventBus;
    private ListFunctions ListFunctions;

    @FXML
    void AddMoreItems(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(CreateListView));
        Parent root = loader.load();
        CreateListController controller = loader.getController();
        controller.setListName(shoppingList.getName());
        controller.AddedListName(event);
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        ConfigPropertiesReader.ReadPropertiesFile();
    }

    @FXML
    void SwitchToHomeScene(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(HomeView));
        Parent root = loader.load();
        HomeController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        ConfigPropertiesReader.ReadPropertiesFile();
    }

    @FXML
    void SwitchToMyListsScene(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MyListsView));
        Parent root = loader.load();
        MyListsController controller = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        ConfigPropertiesReader.ReadPropertiesFile();
    }

    void setShoppingList(ShoppingList givenShoppingList) throws IOException {

        ConfigPropertiesReader.ReadPropertiesFile();
        ListFunctions = new ListFunctions();
        eventBus = ListFunctions.getEventBus();
        shoppingList = givenShoppingList;
        ListTitle.setText(shoppingList.getName());
        ObservableList<String> ListOfProducts = FXCollections.observableArrayList();
        List<Product> products = shoppingList.getProducts();
        if(products.isEmpty())
        {
            ProductsList.setVisible(false);
            CompletedListText.setVisible(true);
        }
        else
        {
            List<String> productsWithDetails = new ArrayList<>();
            for(Product product : products)
            {
                String productName = product.getName();
                int quantity = product.getQuantity();
                double price = product.getPrice();
                String ProductDetails = quantity + " | " + productName + " | " + price + " zl";
                productsWithDetails.add(ProductDetails);
                totalPrice = totalPrice + price;
            }
            ListOfProducts.addAll(productsWithDetails);
            ProductsList.setItems(ListOfProducts);
            PriceLabel.setText(String.valueOf(totalPrice) + " zl");
            eventBus.register(new DeletedProductFromListSubscriber(ProductsList, totalPrice, PriceLabel, CompletedListText));

            EventHandler<MouseEvent> doubleClickHandler = event -> {
                if (event.getClickCount() == 2) {
                    String selectedProductDetails = ProductsList.getSelectionModel().getSelectedItem();
                    String[] StringParts = selectedProductDetails.split(" \\| ");
                    String selectedProductName = StringParts[1];
                    int selectedQuantity = Integer.parseInt(StringParts[0]);
                    double selectedProductPrice = Double.parseDouble(StringParts[2].replace(" zl", ""));
                    ListFunctions.deleteProduct(shoppingList.getName(), selectedProductName, selectedProductPrice, selectedQuantity);
                }
            };

            ProductsList.addEventHandler(MouseEvent.MOUSE_CLICKED, doubleClickHandler);
        }
    }
}
