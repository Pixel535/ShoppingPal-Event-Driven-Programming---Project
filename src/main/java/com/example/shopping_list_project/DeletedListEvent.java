package com.example.shopping_list_project;

public class DeletedListEvent {
    private final String listName;

    public DeletedListEvent(String listName) {
        this.listName = listName;
    }

    public String getListName() {
        return listName;
    }
}
