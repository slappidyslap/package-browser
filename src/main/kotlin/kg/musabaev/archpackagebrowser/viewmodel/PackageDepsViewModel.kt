package kg.musabaev.archpackagebrowser.viewmodel

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import kg.musabaev.archpackagebrowser.core.PackageManager
import kotlinx.coroutines.*
import kotlinx.coroutines.javafx.JavaFx
import org.slf4j.LoggerFactory

class PackageDepsViewModel(
    private val packageManager: PackageManager,
) {
    private val log = LoggerFactory.getLogger(PackageDepsViewModel::class.java)
    private val scope = CoroutineScope(Dispatchers.JavaFx)
    private var loadJob: Job? = null

    val packageName: StringProperty = SimpleStringProperty("")
    val isLoading: BooleanProperty = SimpleBooleanProperty(false)
    val depsTree: StringProperty = SimpleStringProperty("")

    fun loadPackageDeps(name: String) {
        loadJob?.cancel()
        log.info("Loading deps of the package $name")
        packageName.set(name)
        loadJob = scope.launch {
            try {
                isLoading.set(true)
                val result = withContext(Dispatchers.IO) {
                    packageManager.getPackageDeps(name)
                }
                depsTree.set(result)
                log.info("Deps of the package $name loaded")
            } finally {
                isLoading.set(false)
            }
        }
    }
}
