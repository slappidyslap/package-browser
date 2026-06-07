package kg.musabaev.archpackagebrowser

import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.stage.Stage
import kg.musabaev.archpackagebrowser.core.PacmanPackageManager
import kg.musabaev.archpackagebrowser.view.PackageDepsView
import kg.musabaev.archpackagebrowser.view.PackageDetailsView
import kg.musabaev.archpackagebrowser.view.PackageListView
import kg.musabaev.archpackagebrowser.viewmodel.PackageDepsViewModel
import kg.musabaev.archpackagebrowser.viewmodel.PackageDetailsViewModel
import kg.musabaev.archpackagebrowser.viewmodel.PackageListViewModel

class MainApplication : Application() {
    override fun start(stage: Stage) {
        val scene = Scene(buildGui())
        stage.title = "Package browser"
        stage.scene = scene
        stage.isMaximized = true
        stage.show()
    }

    fun buildGui(): Parent {
        val pacman = PacmanPackageManager()
        val packageListViewModel = PackageListViewModel(pacman)
        val packageDetailsViewModel = PackageDetailsViewModel(pacman)
        val packageDepsViewModel = PackageDepsViewModel(pacman)
        val packageListView = PackageListView(packageListViewModel)
        val packageDetailsView = PackageDetailsView(packageDetailsViewModel, packageListViewModel)
        val packageDepsView = PackageDepsView(packageDepsViewModel, packageListViewModel)

        val splitPane = SplitPane().apply {
            orientation = Orientation.HORIZONTAL
            items.add(packageListView)
            items.add(SplitPane().apply {
                orientation = Orientation.VERTICAL
                items.add(packageDetailsView)
                items.add(packageDepsView)
            })
        }
        return splitPane
    }
}
