package kg.musabaev.archpackagebrowser.core

interface PackageManager {

    /**
     * Возвращает список всех установленных в системе пакетов.
     */
    fun getInstalledPackages(): List<String>

    fun getPackageDetails(name: String): Map<String, String>
}
