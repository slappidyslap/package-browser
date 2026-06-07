package kg.musabaev.archpackagebrowser.core

import org.slf4j.LoggerFactory

class PacmanPackageManager : PackageManager {

    // Записи которые не должны присутствовать в деталях
    private val EXCLUDED_PACKAGE_DETAIL_ENTRIES = listOf("Name", "Depends On")
    // Запись об описании пакета должно идти первым в деталях
    private val DESCRIPTION_ENTRY_NAME = "Description"

    private val log = LoggerFactory.getLogger(PacmanPackageManager::class.java)

    override fun getInstalledPackages(): List<String> {
        log.info("Getting installed packages")
        log.info("Executing \"pacman -Qq\"")
        val process = ProcessBuilder("pacman", "-Qq").start()

        val code: Int = process.waitFor()
        if (code != 0) {
            log.warn("The command \"pacman -Qq\" failed with {}", code)
            return emptyList()
        }
        val packages: List<String> = process.inputReader().readLines()
        log.info("The command \"pacman -Qq\" finished")
        return packages
    }

    override fun getPackageDetails(name: String): Map<String, String> {
        log.info("Getting details of the installed package")
        log.info("Executing \"pacman -Qi $name\"")
        val process = ProcessBuilder("pacman", "-Qi", name).start()

        val code: Int = process.waitFor()
        if (code != 0) {
            log.warn("The command \"pacman -Qi $name\" failed with {}", code)
            return emptyMap()
        }

        val details = process
            .inputStream
            .reader()
            .readLines()
            .filter { it.isNotBlank() }
            .map { detail -> detail.split(":") }
            .filterNot { it[0] in EXCLUDED_PACKAGE_DETAIL_ENTRIES }
            .associate { details -> details[0].trimEnd() to details[1].trim() }
        log.info("The command \"pacman -Qi $name\" finished")
        return details
    }
}
