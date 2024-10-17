import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import com.vanniktech.maven.publish.SonatypeHost
import org.hidetake.gradle.swagger.generator.GenerateSwaggerCode

plugins {
    kotlin("jvm")
    alias(libs.plugins.swagger.codegen)
    alias(libs.plugins.maven.publish)
}

repositories {
    mavenCentral()
}

dependencies {
    swaggerCodegen(libs.openapi.generator)

    // Dependencies of the generated code. Check out `build.gradle` in your build folder later if you're curious.
    implementation(libs.moshi.kotlin)
    implementation(libs.moshi.adapters)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotest.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.assertions.json)
}

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()
}

tasks.test {
    useJUnitPlatform()
}

swaggerSources {
  register("valhalla") {
      val openApiFile = file("./openapi.yaml")
      require(openApiFile.exists()) { "OpenAPI spec file not found: $openApiFile" }

      setInputFile(openApiFile)
      code(delegateClosureOf<GenerateSwaggerCode> {
          language = "kotlin"
          components = listOf("models")
          additionalProperties = mapOf(
              "groupId" to "com.valhalla",
              "packageName" to "com.valhalla.api"
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

tasks.named("sourcesJar") {
    dependsOn(tasks.generateSwaggerCode)
}


sourceSets {
    val main by getting
    val valhalla by swaggerSources.getting
    main.kotlin.srcDir("${valhalla.code.outputDir}/src/main/kotlin")
}

val libraryVersion: String by extra

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()

    if (libraryVersion == "unspecified") {
        throw GradleException("libraryVersion must be specified in settings.gradle.kts")
    }

    coordinates("io.github.rallista", "valhalla-models-config", libraryVersion)

    // TODO: Convert to Dokka?
    configure(KotlinJvm(sourcesJar = true, javadocJar = JavadocJar.Javadoc()))

    pom {
        name.set("Valhalla Config Models")
        description.set("Serializable objects to build Valhalla's config JSON.")
        url.set(ProjectConfig.PROJECT_URL)

        licenses {
            license {
                name.set(ProjectConfig.LICENSE_NAME)
                url.set(ProjectConfig.LICENSE_URL)
            }
        }

        developers {
            developer {
                id.set(ProjectConfig.DEVELOPER_ID)
                name.set(ProjectConfig.DEVELOPER_NAME)
            }
        }

        scm {
            connection.set(ProjectConfig.SCM_CONNECTION)
            developerConnection.set(ProjectConfig.SCM_DEV_CONNECTION)
            url.set(ProjectConfig.SCM_URL)
        }
    }
}