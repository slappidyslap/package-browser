package kg.musabaev.archpackagebrowser.view

import javafx.collections.MapChangeListener
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import kg.musabaev.archpackagebrowser.viewmodel.PackageDetailsViewModel
import kg.musabaev.archpackagebrowser.viewmodel.PackageListViewModel
import org.slf4j.LoggerFactory

class PackageDetailsView(
    val viewModel: PackageDetailsViewModel,
    val packageListViewModel: PackageListViewModel
) : VBox(.0) {

    private val log = LoggerFactory.getLogger(PackageDetailsView::class.java)
    private val name: Label
    private val gridPane: GridPane

    init {
        log.info("Initializing PackageDetailsView")

        name = Label()
        gridPane = GridPane()

        initBinds()
        initComponents()

        log.info("PackageDetailsView initialized")
    }

    private fun initBinds() {
        log.info("Initializing bindings of the PackageDetailsView")

        packageListViewModel.selectedPackage.addListener { _, _, newValue ->
            name.text = newValue
            viewModel.loadPackageDetails(newValue)
        }
        viewModel.details.addListener { _: MapChangeListener.Change<out String, out String>  ->
            rebuildGridPane()
        }
        log.info("The bindings of the PackageDetailsView initialized")
    }

    private fun initComponents() {
        log.info("Initializing components of the PackageListView")

        super.children.add(name)
        super.children.add(gridPane)

        log.info("Components of the PackageListView initialized")
    }

    private fun rebuildGridPane() {
        gridPane.children.clear()
        viewModel.details.onEachIndexed { i, entry ->
            gridPane.addRow(i, Label(entry.key), Label(entry.value))
        }
    }
}
