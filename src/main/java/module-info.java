module kg.musabaev.archpackagebrowser {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens kg.musabaev.archpackagebrowser to javafx.fxml;
    exports kg.musabaev.archpackagebrowser;
}