import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import java.io.BufferedReader

plugins {
  kotlin("jvm") version "2.1.10"
  id("org.jetbrains.dokka") version "2.0.0"
  `maven-publish`
}

group = "org.veupathdb.vdi"
version = "17.1.0"
description = "Common components for VDI projects"

repositories {
  mavenCentral()
  maven {
    name = "GitHubPackages"
    url  = uri("https://maven.pkg.github.com/veupathdb/maven-packages")
    credentials {
      username = if (extra.has("gpr.user")) extra["gpr.user"] as String? else System.getenv("GITHUB_USERNAME")
      password = if (extra.has("gpr.key")) extra["gpr.key"] as String? else System.getenv("GITHUB_TOKEN")
    }
  }
}

dependencies {
  implementation(libs.json)

  implementation(libs.ldap)
  implementation(libs.coroutines)
  implementation(libs.compression)

  testImplementation(libs.junit.api)
  testRuntimeOnly(libs.junit.engine)
  testImplementation(libs.mockito)
}

tasks.test {
  useJUnitPlatform()

  testLogging {
    events(
      TestLogEvent.FAILED,
      TestLogEvent.STANDARD_OUT,
      TestLogEvent.STANDARD_ERROR,
      TestLogEvent.PASSED
    )

    exceptionFormat = TestExceptionFormat.FULL
    showExceptions = true
    showCauses = true
    showStackTraces = true
  }
}

kotlin {
  jvmToolchain {
    languageVersion = JavaLanguageVersion.of(21)
    vendor = JvmVendorSpec.AMAZON
  }
}

java {
  withSourcesJar()
}

val docVersion = with(project.version as String) { substring(0, lastIndexOf('.')) } + ".0"
val dokkaPath = "docs/dokka/$docVersion"

val updateReadme = tasks.register("update-readme-version") {
  with(ProcessBuilder("sed", "-i", "s/:lib-version: .\\+/:lib-version: ${project.version}/;s/:lib-feature: .\\+/:lib-feature: $docVersion/", "readme.adoc").start()) {
    require(waitFor() == 0) { "failed to update readme!\n" + errorStream.bufferedReader().use(BufferedReader::readText) }
  }
}

tasks.dokkaHtml {
  outputDirectory.set(file(dokkaPath))
  finalizedBy(updateReadme)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
  dependsOn(tasks.dokkaHtml)
  archiveClassifier.set("javadoc")
  from(file(dokkaPath))
}

tasks.withType<Jar> {
  enabled = true
}

publishing {
  repositories {
    maven {
      name = "GitHub"
      url = uri("https://maven.pkg.github.com/VEuPathDB/vdi-component-common")
      credentials {
        username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
        password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
      }
    }
  }

  publications {

    create<MavenPublication>("gpr") {
      from(components["java"])

      artifact(javadocJar)

      pom {
        name.set("vdi-component-common")
        description.set(project.description)
        url.set("https://github.com/VEuPathDB/vdi-component-common")

        licenses {
          license {
            name.set("Apache-2.0")
          }
        }

        developers {
          developer {
            id.set("epharper")
            name.set("Elizabeth Paige Harper")
            email.set("foxcapades.io@gmail.com")
            url.set("https://github.com/foxcapades")
          }
        }

        scm {
          connection.set("scm:git:git://github.com/VEuPathDB/vdi-component-common.git")
          developerConnection.set("scm:git:ssh://github.com/VEuPathDB/vdi-component-common.git")
          url.set("https://github.com/VEuPathDB/vdi-component-common")
        }
      }
    }
  }
}
