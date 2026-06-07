package kg.musabaev.archpackagebrowser

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.stage.Stage
import kg.musabaev.archpackagebrowser.core.PacmanPackageManager
import kg.musabaev.archpackagebrowser.view.PackageDetailsView
import kg.musabaev.archpackagebrowser.view.PackageListView
import kg.musabaev.archpackagebrowser.viewmodel.PackageDetailsViewModel
import kg.musabaev.archpackagebrowser.viewmodel.PackageListViewModel

class MainApplication : Application() {
    override fun start(stage: Stage) {
        val pacman = PacmanPackageManager()
        val packageListViewModel = PackageListViewModel(pacman)
        val packageDetailsViewModel = PackageDetailsViewModel(pacman)
        val packageListView = PackageListView(packageListViewModel)
        val packageDetailsView = PackageDetailsView(packageDetailsViewModel, packageListViewModel)

        val splitPane = SplitPane()
        splitPane.items.add(packageListView)
        splitPane.items.add(packageDetailsView)

        val scene = Scene(splitPane)
        stage.title = "Package browser"
        stage.scene = scene
        stage.isMaximized = true
        stage.show()
    }
}
