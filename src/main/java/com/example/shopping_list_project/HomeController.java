package com.example.shopping_list_project;

import com.example.shopping_list_project.Config.ConfigPropertiesReader;
import com.example.shopping_list_project.ListsAndProducts.ListFunctions;
import com.example.shopping_list_project.ListsAndProducts.ShoppingList;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class HomeController implements Initializable {

    @FXML
    private Button HomeButton;
    @FXML
    private Button MyListsButton;
    @FXML
    private Label NoListText;
    @FXML
    private AnchorPane NoListsAnchorPane;
    @FXML
    private Button NoListsButton;
    @FXML
    private Button bar1;
    @FXML
    private Button bar2;
    @FXML
    private ImageView fastShoppingCart;
    @FXML
    private AnchorPane menu;
    @FXML
    private ListView<String> ListOfLists;

    private com.example.shopping_list_project.ListsAndProducts.ListFunctions ListFunctions;

    public static String ListView;
    public static String HomeView;
    public static String MyListsView;
    public static String CreateListView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ConfigPropertiesReader.ReadPropertiesFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(System.getProperty("user.dir") + "\\src\\img\\FastShoppingCart.png");
        bar2.setVisible(false);
        menu.setTranslateX(-300);
        fastShoppingCart.setImage(image);
        ListFunctions = new ListFunctions();
        CompletableFuture<List<ShoppingList>> AllListsFuture = ListFunctions.getAllShoppingLists();
        List<ShoppingList> AllLists = AllListsFuture.join();

        if(AllLists.isEmpty())
        {
            NoListsAnchorPane.setVisible(true);
            NoListText.setVisible(true);
            NoListsButton.setVisible(true);
            ListOfLists.setVisible(false);
        }
        else
        {
            ObservableList<String> AllListsNames = FXCollections.observableArrayList();
            List<String> names = AllLists.stream().map(ShoppingList::getName).toList();
            AllListsNames.addAll(names);
            ListOfLists.setItems(AllListsNames);

            ListOfLists.setOnMouseClicked(event -> {
                String selectedListName = ListOfLists.getSelectionModel().getSelectedItem();
                ShoppingList selectedList = AllLists.stream().filter(list -> list.getName().equals(selectedListName)).findFirst().orElse(null);
                if (selectedList != null) {
                    showShoppingListDetails(selectedList, event);
                }
            });
        }
    }

    @FXML
    void showShoppingListDetails(ShoppingList shoppingList, MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ListView));
            Parent root = loader.load();
            ListController controller = loader.getController();
            controller.setShoppingList(shoppingList);
            Scene scene = new Scene(root);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
            ConfigPropertiesReader.ReadPropertiesFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void MenuSlideIn(MouseEvent event) {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menu);

        slide.setToX(0);
        slide.play();

        menu.setTranslateX(-300);

        slide.setOnFinished((ActionEvent e) -> {
            bar1.setVisible(false);
            bar2.setVisible(true);
        });
    }

    @FXML
    void MenuSlideOut(MouseEvent event) {

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(menu);

        slide.setToX(-300);
        slide.play();

        menu.setTranslateX(0);

        slide.setOnFinished((ActionEvent e) -> {
            bar1.setVisible(true);
            bar2.setVisible(false);
        });
    }

    @FXML
    void AddLists(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(CreateListView));
        Parent root = loader.load();
        CreateListController controller = loader.getController();
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
}
