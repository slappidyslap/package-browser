package kg.musabaev.archpackagebrowser

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.stage.Stage
import kg.musabaev.archpackagebrowser.core.PacmanPackageManager

class MainApplication : Application() {
    override fun start(stage: Stage) {
        val packageListViewModel = PackageListViewModel(PacmanPackageManager())
        val splitPane = SplitPane()
        splitPane.items.add(PackageListView(packageListViewModel))
        val scene = Scene(splitPane)
        stage.title = "Hello!"
        stage.scene = scene
        stage.isMaximized = true
        stage.show()

    }
}
