import java.util.Properties

val versions = Properties()
file("gradle.properties").inputStream().use { versions.load(it) }

pluginManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":app")
include(":database")
include(":network")
