package kg.musabaev.packagebrowser.view

import javafx.collections.ListChangeListener
import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.layout.GridPane
import javafx.scene.layout.VBox
import kg.musabaev.packagebrowser.viewmodel.PackageDetailsViewModel
import kg.musabaev.packagebrowser.viewmodel.PackageListViewModel
import org.slf4j.LoggerFactory

class PackageDetailsView(
    val viewModel: PackageDetailsViewModel,
    val packageListViewModel: PackageListViewModel
) : VBox(.0) {

    private val log = LoggerFactory.getLogger(PackageDetailsView::class.java)
    private val name: Label
    private val loadIndicator: ProgressIndicator
    private val gridPane: GridPane

    init {
        log.info("Initializing PackageDetailsView")

        name = Label()
        loadIndicator = ProgressIndicator()
        gridPane = GridPane()

        initBinds()
        initComponents()

        log.info("PackageDetailsView initialized")
    }

    private fun initBinds() {
        log.info("Initializing bindings of the PackageDetailsView")

        packageListViewModel.selectedPackageName.bindBidirectional(viewModel.packageName)

        name.textProperty().bind(viewModel.packageName)
        loadIndicator.visibleProperty().bind(viewModel.isLoading)
        gridPane.visibleProperty().bind(viewModel.isLoading.not())

        viewModel.details.addListener(ListChangeListener {
            rebuildGridPane()
        })
        log.info("The bindings of the PackageDetailsView initialized")
    }

    private fun initComponents() {
        log.info("Initializing components of the PackageDetailsView")

        super.children.add(name)
        super.children.add(loadIndicator)
        super.children.add(gridPane)

        log.info("Components of the PackageDetailsView initialized")
    }

    private fun rebuildGridPane() {
        gridPane.children.clear()
        viewModel.details.onEachIndexed { i, entry ->
            gridPane.addRow(i, Label(entry.first), Label(entry.second))
        }
    }
}
