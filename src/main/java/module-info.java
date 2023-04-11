module com.example.shopping_list_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires mongo.java.driver;
    requires org.json;
    requires org.apache.commons.lang3;
    requires com.google.common;

    opens com.example.shopping_list_project to javafx.fxml;
    exports com.example.shopping_list_project;
    exports com.example.shopping_list_project.DataBase;
    opens com.example.shopping_list_project.DataBase to javafx.fxml;
    exports com.example.shopping_list_project.Config;
    opens com.example.shopping_list_project.Config to javafx.fxml;
    exports com.example.shopping_list_project.EventsAndSubscribers;
    opens com.example.shopping_list_project.EventsAndSubscribers to javafx.fxml;
    exports com.example.shopping_list_project.ListsAndProducts;
    opens com.example.shopping_list_project.ListsAndProducts to javafx.fxml;
}