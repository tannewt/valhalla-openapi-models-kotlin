plugins {
    kotlin("jvm") version "1.8.22" apply false
}

rootProject.name = "valhalla-openapi-models-kotlin"

gradle.beforeProject {
    extensions.extraProperties["libraryVersion"] = "0.0.6"
}

include("client")
include("config")