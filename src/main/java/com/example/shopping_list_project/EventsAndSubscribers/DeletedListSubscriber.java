package com.example.shopping_list_project.EventsAndSubscribers;

import com.example.shopping_list_project.EventsAndSubscribers.DeletedListEvent;
import com.google.common.eventbus.Subscribe;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class DeletedListSubscriber {
    private final ListView<String> ListOfLists;
    private Button SearchButton;
    private Label searchLabel;
    private TextField SearchBar;
    private Button RemoveButton;
    private Button OpenButton;
    private Label NoListsLabel;

    public DeletedListSubscriber(ListView<String> ListOfLists, Button SearchButton, Label searchLabel,
                                 TextField SearchBar, Button RemoveButton, Button OpenButton, Label NoListsLabel) {
        this.ListOfLists = ListOfLists;
        this.SearchButton = SearchButton;
        this.searchLabel = searchLabel;
        this.SearchBar = SearchBar;
        this.RemoveButton = RemoveButton;
        this.OpenButton = OpenButton;
        this.NoListsLabel = NoListsLabel;
    }
    @Subscribe
    public void handleShoppingListDeletedEvent(DeletedListEvent event) {
        Platform.runLater(() -> {
            ListOfLists.getItems().remove(event.getListName());

            if(ListOfLists.getItems().isEmpty())
            {
                SearchButton.setVisible(false);
                searchLabel.setVisible(false);
                SearchBar.setVisible(false);
                RemoveButton.setVisible(false);
                OpenButton.setVisible(false);
                ListOfLists.setVisible(false);
                NoListsLabel.setVisible(true);
            }
        });
    }
}
