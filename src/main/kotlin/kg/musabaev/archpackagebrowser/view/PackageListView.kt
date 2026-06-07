package kg.musabaev.archpackagebrowser.view

import javafx.beans.binding.Bindings
import javafx.scene.control.ListView
import javafx.scene.layout.VBox
import kg.musabaev.archpackagebrowser.viewmodel.PackageListViewModel
import org.slf4j.LoggerFactory

class PackageListView(
    val viewModel: PackageListViewModel
) : VBox(.0) {

    private val log = LoggerFactory.getLogger(PackageListView::class.java)
    private val listView: ListView<String>

    init {
        log.info("Initializing PackageListView")

        listView = ListView<String>()

        initBinds()
        initComponents()

        log.info("PackageListView initialized")
    }

    private fun initBinds() {
        log.info("Initializing bindings of the PackageListView")

        Bindings.bindContentBidirectional(listView.items, viewModel.packages)
        listView.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            viewModel.selectedPackage.value = newValue
        }
        log.info("Bindings of the PackageListView initialized")
    }

    private fun initComponents() {
        log.info("Initializing components of the PackageListView")

        viewModel.loadPackages()

        super.children.addAll(listView)

        log.info("Components of the PackageListView initialized")
    }
}
