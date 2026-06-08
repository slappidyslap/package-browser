package kg.musabaev.packagebrowser

import javafx.application.Application
import javafx.geometry.Orientation
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.stage.Stage
import kg.musabaev.packagebrowser.core.PacmanPackageManager
import kg.musabaev.packagebrowser.view.PackageDepsView
import kg.musabaev.packagebrowser.view.PackageDetailsView
import kg.musabaev.packagebrowser.view.PackageListView
import kg.musabaev.packagebrowser.viewmodel.PackageDepsViewModel
import kg.musabaev.packagebrowser.viewmodel.PackageDetailsViewModel
import kg.musabaev.packagebrowser.viewmodel.PackageListViewModel

class MainApplication : Application() {
    override fun start(stage: Stage) {
        val scene = Scene(buildGui())
        scene.stylesheets.add(javaClass.getResource("/style.css")!!.toExternalForm())
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
