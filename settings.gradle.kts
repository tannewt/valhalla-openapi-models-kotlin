plugins {
    kotlin("jvm") version "1.8.22" apply false
}

rootProject.name = "valhalla-api-kotlin"

gradle.beforeProject {
    extensions.extraProperties["libraryVersion"] = "0.0.2"
}

include("client")
include("config")