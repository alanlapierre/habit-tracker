plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.serialization") version "1.9.21"
    application
}

group = "com.alapierre"

version = "1.0-SNAPSHOT"

repositories { mavenCentral() }

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.mongodb:mongodb-driver-sync:4.10.0")
    implementation(kotlin("stdlib"))
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(21) }

application {
    mainClass.set("com.alapierre.MainKt")
}


