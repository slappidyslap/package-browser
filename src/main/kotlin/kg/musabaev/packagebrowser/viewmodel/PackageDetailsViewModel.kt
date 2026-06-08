package kg.musabaev.packagebrowser.viewmodel

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kg.musabaev.packagebrowser.core.Entry
import kg.musabaev.packagebrowser.core.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class PackageDetailsViewModel(
    private val packageManager: PackageManager,
) : BasePackageViewModel() {
    private val log = LoggerFactory.getLogger(PackageDetailsViewModel::class.java)

    val details: ObservableList<Entry> = FXCollections.observableArrayList()

    init {
        packageName.addListener { _, _, new ->
            loadPackageDetails(new)
        }
    }

    private fun loadPackageDetails(name: String) {
        log.info("Loading package details of the {}", name)
        packageName.set(name)
        loadWithLoading {
            val result = withContext(Dispatchers.IO) {
                packageManager.getPackageDetails(name)
            }
            details.setAll(result)
            log.info("Package details of the {} loaded", name)
        }
    }
}
