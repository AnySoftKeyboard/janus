plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

import com.anysoftkeyboard.tools.dependencies.Versions

android {
    namespace = "com.anysoftkeyboard.janus.app"
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "com.anysoftkeyboard.janus"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    kotlin {
        jvmToolchain(17)
    }
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

dependencies {
    implementation(project(":database"))
    implementation(project(":network"))
    implementation("androidx.core:core-ktx:${Versions.coreKtx}")
    implementation("androidx.appcompat:appcompat:${Versions.appcompat}")
    implementation("com.google.android.material:material:${Versions.material}")
    testImplementation("junit:junit:${Versions.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Versions.testExtJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Versions.espressoCore}")
}
