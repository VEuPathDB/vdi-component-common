import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
  kotlin("jvm") version "2.0.21"
  id("org.jetbrains.dokka") version "1.9.20"
  `maven-publish`
}

group = "org.veupathdb.vdi"
version = "13.1.0"
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
  implementation("org.veupathdb.vdi:vdi-component-json:1.0.2")

  implementation("org.unbroken-dome.base62:base62:1.1.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
  implementation("org.apache.commons:commons-compress:1.27.1")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
  testImplementation("org.mockito:mockito-core:5.14.2")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.3")
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

val dokkaPath = "docs/dokka/${(project.version as String).let { it.substring(0, it.lastIndexOf('.')) }}.0"

tasks.dokkaHtml {
  outputDirectory.set(file(dokkaPath))
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
