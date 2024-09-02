import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode

plugins {
    kotlin("jvm")
    id("org.hidetake.swagger.generator") version "2.19.2"
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    swaggerCodegen("org.openapitools:openapi-generator-cli:7.5.0")

    // Dependencies of the generated code. Check out `build.gradle` in your build folder later if you're curious.
    val moshiVersion = "1.15.1"
    implementation("com.squareup.moshi:moshi-kotlin:$moshiVersion")
    implementation("com.squareup.moshi:moshi-adapters:$moshiVersion")

    testImplementation(kotlin("test"))

    val kotestVersion = "5.9.0"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-json:$kotestVersion")
//    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

swaggerSources {
  register("valhalla") {
      val openApiFile = file("../openapi-config.yaml")
      require(openApiFile.exists()) { "OpenAPI spec file not found: $openApiFile" }

      setInputFile(openApiFile)
      code(delegateClosureOf<GenerateSwaggerCode> {
          language = "kotlin"
          components = listOf("models")
          additionalProperties = mapOf(
              "groupId" to "com.valhalla",
              "packageName" to "com.valhalla.config"
          )
          rawOptions = listOf(
              "--skip-validate-spec"
          )
      })
  }
}

// Comment this out if you do NOT want the code gen to run every time you build.
// There is an HTTP cache by default, so it won't necessarily make a request every single build.
tasks.compileKotlin.configure {
    dependsOn(tasks.generateSwaggerCode)
}

sourceSets {
    val main by getting
    val valhalla by swaggerSources.getting
    main.kotlin.srcDir("${valhalla.code.outputDir}/src/main/kotlin")
}

val libraryVersion: String by extra

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/rallista/valhalla-openapi-models-kotlin")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])

            groupId = "com.valhalla"
            artifactId = "config"
            version = libraryVersion
        }
    }
}
