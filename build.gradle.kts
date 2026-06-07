plugins {
    java
    application
    id("org.jetbrains.kotlin.jvm") version "2.1.20"
    id("org.javamodularity.moduleplugin") version "1.8.15"
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "4.0.2"
}

group = "kg.musabaev"
version = "0.0.1"

repositories {
    mavenCentral()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

application {
    mainModule.set("kg.musabaev.packagebrowser")
    mainClass.set("kg.musabaev.packagebrowser.MainApplication")
}
kotlin {
    jvmToolchain(21)
}

val junitVersion = "5.12.1"
val javaFxVersion = "21.0.6"
val logbackVersion = "1.5.34"
val kotlinxCoroutineVersion = "1.11.0"

javafx {
    version = javaFxVersion
    modules = listOf("javafx.controls")
}

dependencies {
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:$kotlinxCoroutineVersion")

    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jlink {
    imageZip.set(
        file("build/dist/${rootProject.name}-${javafx.platform.classifier}-${javafx.platform.arch}-${version}.zip"))
    options.set(
        listOf(
            "--strip-debug",
            "--compress", "zip-6",
            "--no-header-files",
            "--no-man-pages"
        )
    )
    launcher {
        name = rootProject.name
    }
}
