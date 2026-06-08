package kg.musabaev.packagebrowser.core

import org.slf4j.LoggerFactory

class PacmanPackageManager : PackageManager {

    // Записи которые не должны присутствовать в деталях
    private val EXCLUDED_PACKAGE_DETAIL_ENTRIES = listOf("Name", "Depends On")
    // Запись об описании пакета должно идти первым в деталях
    private val DESCRIPTION_ENTRY_NAME = "Description"

    private val log = LoggerFactory.getLogger(PacmanPackageManager::class.java)

    override fun getInstalledPackages(): List<String> {
        log.info("Getting installed packages")
        val process = execCommand("pacman", "-Qq") ?: return emptyList()
        val packages: List<String> = process.inputReader().readLines()
        return packages
    }

    override fun getPackageDetails(name: String): List<Entry> {
        log.info("Getting details of the package {}", name)
        val process = execCommand("pacman", "-Qi", name) ?: return emptyList()

        // Сперва найти индекс первого двоеточия, чтобы по нему разделить запись на ключ и значения
        // Это сделано, для того чтобы избежать когда у некоторых записей в значении есть двоеточие
        val outputLines = process
            .inputReader()
            .readLines()
        if (outputLines.isEmpty()) {
            log.warn("Unable to find first colon in 'pacman -Qi {}' process output", name)
            return emptyList()
        }
        val firstColonIndex = outputLines[0].indexOf(":")

        val details = outputLines
            .filter { it.isNotBlank() }
            .map { line -> getPairByPacmanEntry(line, firstColonIndex) }
            .filterNot { it.first in EXCLUDED_PACKAGE_DETAIL_ENTRIES }
        return details
    }

    private fun getPairByPacmanEntry(entry: String, delimiterIndex: Int): Entry {
        return if (entry[delimiterIndex] == ':')
            entry.substring(0 until delimiterIndex).trimEnd() to entry.substring(delimiterIndex + 1).trim()
        else
            "" to entry.substring(delimiterIndex + 1).trim()
    }

    override fun getPackageDeps(name: String): String {
        log.info("Getting dependencies of the package {}", name)
        val process = execCommand("pactree", name) ?: return ""
        return process.inputReader().readText().trim()
    }

    private fun execCommand(command: String, vararg args: String): Process? {
        val commandStr = "$command ${args.joinToString(" ")}"
        log.info("Executing '{}'", commandStr)
        val process = ProcessBuilder(command, *args).start()
        val code = process.waitFor()
        if (code != 0) {
            log.warn("The command '{}' failed with {}", commandStr, code)
            log.warn(process.errorReader().readText())
            return null
        }
        log.info("The command '{}' finished", commandStr)
        return process
    }
}
