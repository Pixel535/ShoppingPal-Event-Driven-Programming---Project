package com.example.shopping_list_project;

import com.google.common.eventbus.EventBus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class MyListsController implements Initializable {

    @FXML
    private Button AddListButton;

    @FXML
    private Button HomeButton;

    @FXML
    private ListView<String> ListOfList;

    @FXML
    private Label NoListsLabel;

    @FXML
    private Button OpenButton;

    @FXML
    private Button RemoveButton;

    @FXML
    private TextField SearchBar;

    @FXML
    private Button SearchButton;

    @FXML
    private Label searchLabel;

    public static String HomeView;
    public static String CreateListView;
    public static String ListView;

    private ListFunctions ListFunctions;
    private List<ShoppingList> AllLists;
    private ObservableList<String> AllListsNames;
    private EventBus eventBus;

    @FXML
    void OpenList(MouseEvent event) throws IOException {
        String selectedListName = ListOfList.getSelectionModel().getSelectedItem();
        ShoppingList selectedList = AllLists.stream().filter(list -> list.getName().equals(selectedListName)).findFirst().orElse(null);
        if (selectedList != null) {
            showShoppingListDetails(selectedList, event);
        }
        ConfigPropertiesReader.ReadPropertiesFile();
    }

    @FXML
    void RemoveList(MouseEvent event) throws IOException {
        String selectedListName = ListOfList.getSelectionModel().getSelectedItem();
        ListFunctions.deleteShoppingListByName(selectedListName);
        SearchBar.clear();
        ConfigPropertiesReader.ReadPropertiesFile();
    }

    @FXML
    void Search(MouseEvent event) throws IOException {
        ListOfList.getItems().clear();
        CompletableFuture<List<ShoppingList>> ListFuture = ListFunctions.findShoppingListByName(SearchBar.getText());
        AllLists = ListFuture.join();
        AllListsNames = FXCollections.observableArrayList();
        List<String> names = AllLists.stream().map(ShoppingList::getName).toList();
        AllListsNames.addAll(names);
        ListOfList.setItems(AllListsNames);
        ConfigPropertiesReader.ReadPropertiesFile();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ConfigPropertiesReader.ReadPropertiesFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ListFunctions = new ListFunctions();
        eventBus = ListFunctions.getEventBus();
        CompletableFuture<List<ShoppingList>> AllListsFuture = ListFunctions.getAllShoppingLists();
        AllLists = AllListsFuture.join();

        if(AllLists.isEmpty())
        {
            SearchButton.setVisible(false);
            searchLabel.setVisible(false);
            SearchBar.setVisible(false);
            RemoveButton.setVisible(false);
            OpenButton.setVisible(false);
            ListOfList.setVisible(false);
            NoListsLabel.setVisible(true);
        }
        else
        {
            AllListsNames = FXCollections.observableArrayList();
            List<String> names = AllLists.stream().map(ShoppingList::getName).toList();
            AllListsNames.addAll(names);
            ListOfList.setItems(AllListsNames);
            eventBus.register(new DeletedListSubscriber(ListOfList, SearchButton, searchLabel, SearchBar, RemoveButton, OpenButton, NoListsLabel));
        }

        SearchBar.clear();
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

}