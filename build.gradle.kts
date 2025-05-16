import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import java.io.BufferedReader

plugins {
  alias(libs.plugins.kotlin)
  alias(libs.plugins.dokka)
  `maven-publish`
}

group = "org.veupathdb.vdi"
version = "18.0.0-b2"
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
  api(libs.compression)

  implementation(libs.json)

  implementation(libs.ldap)
  implementation(libs.coroutines)
}

buildscript {
  dependencies {
    classpath(libs.build.dokka)
  }
}

testing {
  suites {
    withType<JvmTestSuite> {
      useJUnitJupiter(libs.versions.junit)
      dependencies {
        implementation(libs.mockito.core)
        implementation(libs.mockito.junit)
      }
    }
  }
}

tasks.test {
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
val dokkaPath = "docs/dokka/"

val updateReadme = tasks.register("update-readme-version") {
  with(ProcessBuilder("sed", "-i", "s/:lib-version: .\\+/:lib-version: ${project.version}/;s/:lib-feature: .\\+/:lib-feature: $docVersion/", "readme.adoc").start()) {
    require(waitFor() == 0) { "failed to update readme!\n" + errorStream.bufferedReader().use(BufferedReader::readText) }
  }
}

tasks.dokkaHtml {
  outputDirectory.set(file(dokkaPath))

  pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
    footerMessage = "© 2023-2025 VEuPathDB"
    customStyleSheets = listOf(file("docs/css/dokka.css"))
  }
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
