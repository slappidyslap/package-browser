package kg.musabaev.packagebrowser.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import kg.musabaev.packagebrowser.core.PackageManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import java.util.function.Predicate

class PackageListViewModel(
    private val packageManager: PackageManager
) {
    private val log = LoggerFactory.getLogger(PackageListViewModel::class.java)
    private val scope = CoroutineScope(Dispatchers.JavaFx)

    val searchQuery: StringProperty = SimpleStringProperty("")
    val allPackages: ObservableList<String> = FXCollections.observableArrayList()
    val filteredPackages = FilteredList(allPackages) { true }
    val selectedPackageName: StringProperty = SimpleStringProperty("")

    init {
        searchQuery.addListener { _, _, new ->
            filteredPackages.predicate = if (new.isBlank()) {
                Predicate { true }
            } else {
                Predicate<String> { it.contains(new.trim(), ignoreCase = true) }
            }
        }
        loadPackages()
    }

    private fun loadPackages() {
        log.info("Loading packages")
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                packageManager.getInstalledPackages()
            }
            allPackages.setAll(result)
            log.info("Packages loaded")
        }
    }
}
