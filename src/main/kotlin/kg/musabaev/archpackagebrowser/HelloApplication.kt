package kg.musabaev.archpackagebrowser

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.SplitPane
import javafx.stage.Stage
import kg.musabaev.archpackagebrowser.core.PacmanPackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val packageListViewModel = PackageListViewModel()
        val splitPane = SplitPane()
        splitPane.items.add(PackageListView(PacmanPackageManager(), packageListViewModel))
        val scene = Scene(splitPane)
        stage.title = "Hello!"
        stage.scene = scene
        stage.isMaximized = true
        stage.show()

    }
}
