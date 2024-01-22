plugins {
  kotlin("jvm") version "1.9.10"
  id("org.jetbrains.dokka") version "1.9.0"
  `maven-publish`
}

group = "org.veupathdb.vdi"
version = "6.7.0"
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

configurations.all {
  resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

dependencies {
  implementation("org.veupathdb.vdi:vdi-component-json:1.0.0")

  implementation("org.unbroken-dome.base62:base62:1.1.0")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
  implementation("org.apache.commons:commons-compress:1.22")
  implementation("org.slf4j:slf4j-api:1.7.36")
  implementation("org.apache.logging.log4j:log4j-api-kotlin:1.2.0")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
  testImplementation("org.mockito:mockito-core:5.2.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.test {
  useJUnitPlatform()
}

kotlin {
  jvmToolchain(18)
}

java {
  withSourcesJar()
}

tasks.dokkaHtml {
  outputDirectory.set(file("docs/dokka/${project.version}"))
}

val javadocJar = tasks.register<Jar>("javadocJar") {
  dependsOn(tasks.dokkaHtml)
  archiveClassifier.set("javadoc")
  from(file("docs/dokka/${project.version}"))
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
