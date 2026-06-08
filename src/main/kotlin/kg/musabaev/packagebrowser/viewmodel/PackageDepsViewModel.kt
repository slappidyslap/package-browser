package kg.musabaev.packagebrowser.viewmodel

import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import kg.musabaev.packagebrowser.core.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory

class PackageDepsViewModel(
    private val packageManager: PackageManager,
) : BasePackageViewModel() {
    private val log = LoggerFactory.getLogger(PackageDepsViewModel::class.java)

    val depsTree: StringProperty = SimpleStringProperty("")

    init {
        packageName.addListener { _, _, new ->
            loadPackageDeps(new)
        }
    }

    private fun loadPackageDeps(name: String) {
        log.info("Loading deps of the package {}", name)
        packageName.set(name)
        loadWithLoading {
            val result = withContext(Dispatchers.IO) {
                packageManager.getPackageDeps(name)
            }
            depsTree.set(result)
            log.info("Deps of the package {} loaded", name)
        }
    }
}
