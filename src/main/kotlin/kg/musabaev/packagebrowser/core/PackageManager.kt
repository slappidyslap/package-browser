package kg.musabaev.packagebrowser.core

typealias Entry = Pair<String, String>

interface PackageManager {

    /**
     * Возвращает список всех установленных в системе пакетов.
     */
    fun getInstalledPackages(): List<String>

    fun getPackageDetails(name: String): List<Entry>

    fun getPackageDeps(name: String): String
}
