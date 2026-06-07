package kg.musabaev.archpackagebrowser.core

import org.slf4j.LoggerFactory

class PacmanPackageManager : PackageManager {

    private val log = LoggerFactory.getLogger(PacmanPackageManager::class.java)

    override fun getInstalledPackages(): List<String> {
        log.info("Getting installed packages")
        val process = ProcessBuilder("pacman", "-Qq").start()
        val packages: List<String> = process.inputReader().readLines()
        log.info("The command \"pacman -Qq\" has been started")

        val code: Int = process.waitFor()
        if (code != 0) {
            log.warn("The command \"pacman -Qq\" failed with {}", code)
            return emptyList()
        } else {
            return packages
        }
    }
}
