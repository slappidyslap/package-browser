package kg.musabaev.packagebrowser.view

import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.scene.control.ToolBar
import javafx.scene.layout.VBox
import kg.musabaev.packagebrowser.viewmodel.PackageListViewModel
import org.slf4j.LoggerFactory
import java.util.function.Predicate

class PackageListView(
    val viewModel: PackageListViewModel
) : VBox(.0) {

    private val log = LoggerFactory.getLogger(PackageListView::class.java)
    private val toolBar: ToolBar
    private val searchField: TextField
    private val listView: ListView<String>

    init {
        log.info("Initializing PackageListView")

        toolBar = ToolBar()
        searchField = TextField()
        listView = ListView<String>()

        initBinds()
        initComponents()

        log.info("PackageListView initialized")
    }

    private fun initBinds() {
        log.info("Initializing bindings of the PackageListView")

        searchField.textProperty().bindBidirectional(viewModel.searchQuery)

        listView.items = viewModel.filteredPackages
        listView.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            viewModel.selectedPackageName.set(newValue)
        }
        log.info("Bindings of the PackageListView initialized")
    }

    private fun initComponents() {
        log.info("Initializing components of the PackageListView")

        searchField.promptText = "Search"
        toolBar.items.add(searchField)
        super.children.add(toolBar)

        super.children.addAll(listView)

        log.info("Components of the PackageListView initialized")
    }
}
