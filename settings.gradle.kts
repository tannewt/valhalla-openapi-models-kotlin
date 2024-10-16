plugins {
    kotlin("jvm") version "2.0.20" apply false
}

rootProject.name = "valhalla-models"

gradle.beforeProject {
    extensions.extraProperties["libraryVersion"] = "0.0.8"
}

include("client")
include("config")