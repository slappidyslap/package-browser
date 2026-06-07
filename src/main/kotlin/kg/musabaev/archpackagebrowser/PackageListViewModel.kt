package kg.musabaev.archpackagebrowser

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList

class PackageListViewModel {
    val packages: ObservableList<String> = FXCollections.observableArrayList()
    // "" значит не выбран
    val selectedPackage: StringProperty = SimpleStringProperty("")
}
