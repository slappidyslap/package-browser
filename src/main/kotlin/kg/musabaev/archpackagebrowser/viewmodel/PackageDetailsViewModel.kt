package kg.musabaev.archpackagebrowser.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableMap
import kg.musabaev.archpackagebrowser.core.PackageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class PackageDetailsViewModel(
    private val packageManager: PackageManager,
) {
    private val log = LoggerFactory.getLogger(PackageDetailsViewModel::class.java)
    private val scope = CoroutineScope(Dispatchers.JavaFx)

    val details: ObservableMap<String, String> = FXCollections.observableHashMap()
    val packageName = SimpleStringProperty("")

    fun loadPackageDetails(name: String) {
        log.info("Loading package details of the $name")
        packageName.set(name)
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                packageManager.getPackageDetails(name)
            }
            details.clear()
            details.putAll(result)
        }
        log.info("Package details of the $name loaded")
    }
}
