buildscript {
    val versions = java.util.Properties()
    file("gradle.properties").inputStream().use { versions.load(it) }
    extra.set("versions", versions)

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:${versions.getProperty("android_gradle_plugin_version")}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${versions.getProperty("kotlin_gradle_plugin_version")}")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version (extra["versions"] as java.util.Properties).getProperty("android_gradle_plugin_version") apply false
    id("com.android.library") version (extra["versions"] as java.util.Properties).getProperty("android_gradle_plugin_version") apply false
    id("org.jetbrains.kotlin.android") version (extra["versions"] as java.util.Properties).getProperty("kotlin_gradle_plugin_version") apply false
    id("com.google.devtools.ksp") version (extra["versions"] as java.util.Properties).getProperty("ksp_gradle_plugin_version") apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    afterEvaluate {
        project.extensions.findByType<com.android.build.api.dsl.CommonExtension<*, *, *, *, *, *>>()?.apply {
            compileOptions {
                sourceCompatibility = org.gradle.api.JavaVersion.VERSION_17
                targetCompatibility = org.gradle.api.JavaVersion.VERSION_17
            }
        }
    }
}
