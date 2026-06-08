package kg.musabaev.packagebrowser.viewmodel

import javafx.beans.property.BooleanProperty
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch

abstract class BasePackageViewModel {
    protected val scope = CoroutineScope(Dispatchers.JavaFx)
    private var loadJob: Job? = null

    val packageName: StringProperty = SimpleStringProperty("")
    val isLoading: BooleanProperty = SimpleBooleanProperty(false)

    protected fun loadWithLoading(block: suspend CoroutineScope.() -> Unit) {
        loadJob?.cancel()
        loadJob = scope.launch {
            try {
                isLoading.set(true)
                block()
            } finally {
                isLoading.set(false)
            }
        }
    }
}
