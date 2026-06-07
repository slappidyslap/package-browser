module kg.musabaev.archpackagebrowser {
    requires javafx.controls;
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires kotlinx.coroutines.javafx;
    requires org.slf4j;

    opens kg.musabaev.archpackagebrowser to javafx.fxml;
    exports kg.musabaev.archpackagebrowser;
}
