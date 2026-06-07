package kg.musabaev.archpackagebrowser.viewmodel

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kg.musabaev.archpackagebrowser.core.PackageManager
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import org.slf4j.LoggerFactory

class PackageDetailsViewModel(
    private val packageManager: PackageManager,
) {
    private val log = LoggerFactory.getLogger(PackageDetailsViewModel::class.java)
    private val scope = CoroutineScope(Dispatchers.JavaFx)
    private var loadJob: Job? = null

    val packageName = SimpleStringProperty("")
    val isLoading: BooleanProperty = SimpleBooleanProperty(false)
    val details: ObservableList<Pair<String, String>> = FXCollections.observableArrayList()

    fun loadPackageDetails(name: String) {
        loadJob?.cancel()
        log.info("Loading package details of the $name")
        packageName.set(name)
        loadJob = scope.launch {
            try {
                isLoading.set(true)
                val result = withContext(Dispatchers.IO) {
                    packageManager.getPackageDetails(name)
                }
                details.setAll(result)
                log.info("Package details of the $name loaded")
            } finally {
                isLoading.set(false)
            }
        }
    }
}
