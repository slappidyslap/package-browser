module kg.musabaev.archpackagebrowser {
    requires javafx.controls;
    requires kotlin.stdlib;
    requires org.slf4j;


    opens kg.musabaev.archpackagebrowser to javafx.fxml;
    exports kg.musabaev.archpackagebrowser;
}
