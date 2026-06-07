package kg.musabaev.archpackagebrowser

import javafx.beans.binding.Bindings
import javafx.scene.control.ListView
import javafx.scene.layout.HBox
import kg.musabaev.archpackagebrowser.core.PackageManager
import org.slf4j.LoggerFactory

class PackageListView(
    private val packageManager: PackageManager,
    val viewModel: PackageListViewModel
) : HBox(.0) {

    private val log = LoggerFactory.getLogger(PackageListView::class.java)

    private val listView: ListView<String>

    init {
        log.info("Initializing PackageListView")

        listView = ListView<String>()

        initBinds()
        initComponents()
    }

    private fun initBinds() {
        log.info("Initializing bindings of the PackageListView")

        Bindings.bindContentBidirectional(listView.items, viewModel.packages)
        listView.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            viewModel.selectedPackage.value = newValue
        }
    }

    private fun initComponents() {
        log.info("Initializing components of the PackageListView")

        viewModel.packages.addAll(packageManager.getInstalledPackages())

        super.getChildren().addAll(listView)
    }

}
