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
}