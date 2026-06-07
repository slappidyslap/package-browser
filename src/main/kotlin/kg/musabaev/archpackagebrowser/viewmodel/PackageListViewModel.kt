package kg.musabaev.archpackagebrowser.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kg.musabaev.archpackagebrowser.core.PackageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class PackageListViewModel(
    private val packageManager: PackageManager
) {
    private val log = LoggerFactory.getLogger(PackageListViewModel::class.java)
    private val scope = CoroutineScope(Dispatchers.JavaFx)

    val packages: ObservableList<String> = FXCollections.observableArrayList()
    val selectedPackage: StringProperty = SimpleStringProperty("")

    fun loadPackages() {
        log.info("Loading packages")
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                packageManager.getInstalledPackages()
            }
            packages.setAll(result)
            log.info("Packages loaded")
        }
    }
}
