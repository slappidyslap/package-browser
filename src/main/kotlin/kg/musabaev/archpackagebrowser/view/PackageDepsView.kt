package kg.musabaev.archpackagebrowser.view

import javafx.scene.control.Label
import javafx.scene.control.ProgressIndicator
import javafx.scene.control.TextArea
import javafx.scene.layout.VBox
import kg.musabaev.archpackagebrowser.viewmodel.PackageDepsViewModel
import kg.musabaev.archpackagebrowser.viewmodel.PackageListViewModel
import org.slf4j.LoggerFactory

class PackageDepsView(
    val viewModel: PackageDepsViewModel,
    val packageListViewModel: PackageListViewModel
) : VBox(.0) {

    private val log = LoggerFactory.getLogger(PackageDepsView::class.java)
    private val title: Label
    private val loadIndicator: ProgressIndicator
    private val textArea: TextArea

    init {
        log.info("Initializing PackageDepsView")

        title = Label("${viewModel.packageName.get()} dependencies")
        loadIndicator = ProgressIndicator()
        textArea = TextArea()

        initBinds()
        initComponents()

        log.info("PackageDepsView initialized")
    }

    private fun initBinds() {
        log.info("Initializing bindings of the PackageDepsView")

        title.textProperty().bind(viewModel.packageName)
        loadIndicator.visibleProperty().bind(viewModel.isLoading)
        textArea.visibleProperty().bind(viewModel.isLoading.not())
        textArea.textProperty().bind(viewModel.depsTree)

        packageListViewModel.selectedPackageName.bindBidirectional(viewModel.packageName)
        packageListViewModel.selectedPackageName.addListener { _, _, newValue ->
            viewModel.loadPackageDeps(newValue)
        }
        log.info("The bindings of the PackageDepsView initialized")
    }

    private fun initComponents() {
        log.info("Initializing components of the PackageDepsView")

        super.children.add(title)
        super.children.add(loadIndicator)
        super.children.add(textArea)

        log.info("Components of the PackageDepsView initialized")
    }
}
