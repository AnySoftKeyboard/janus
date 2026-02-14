plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  alias(libs.plugins.ksp)
  id("dagger.hilt.android.plugin")
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.dropshots)
}

android {
  namespace = "com.anysoftkeyboard.janus.app"
  compileSdk = libs.versions.compileSdk.get().toInt()

  dependenciesInfo {
    // Reproducible builds
    includeInApk = false
    includeInBundle = false
  }

  defaultConfig {
    applicationId = "com.anysoftkeyboard.janus"
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.targetSdk.get().toInt()
    versionCode = 11
    versionName = "0.1.6"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  signingConfigs {
    create("release") {
      storeFile = System.getenv("KEYSTORE_FILE")?.let { file(it) }
      storePassword = System.getenv("KEYSTORE_PASSWORD")
      keyAlias = System.getenv("KEY_ALIAS")
      keyPassword = System.getenv("KEY_PASSWORD")
    }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      signingConfig = signingConfigs.getByName("release")
    }
  }
  kotlin { jvmToolchain(21) }
  buildFeatures { compose = true }
  configurations.all { resolutionStrategy.force("org.jetbrains.kotlin:kotlin-metadata-jvm:2.3.10") }

  testOptions {
    unitTests.isIncludeAndroidResources = true
    managedDevices {
      devices {
        // You can name this whatever you want
        register<com.android.build.api.dsl.ManagedVirtualDevice>("pixel6Api33") {
          device = "Pixel 6"
          apiLevel = 33
          systemImageSource = "google"
        }
      }
    }
  }

  lint { disable += "MissingTranslation" }
}

dependencies {
  implementation(project(":database"))
  implementation(project(":network"))
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.com.google.android.material)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.animation)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.compose.material.icons.extended)
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  implementation(libs.retrofit)
  implementation(libs.retrofit.converter.moshi)
  implementation(libs.okhttp)
  implementation(libs.okhttp.logging.interceptor)
  implementation(libs.moshi)
  implementation(libs.moshi.kotlin)
  implementation(libs.google.mlkit.genai.prompt)
  implementation(libs.hilt.android)
  implementation(libs.hilt.navigation.compose)
  ksp(libs.hilt.compiler)

  testImplementation(libs.junit)
  testImplementation(libs.robolectric)
  testImplementation("org.mockito.kotlin:mockito-kotlin:6.2.2")
  testImplementation(libs.androidx.compose.ui.test.junit4)
  testImplementation(libs.androidx.compose.ui.test.manifest)
  testImplementation(libs.cash.turbine)
  testImplementation(libs.hilt.android.testing)
  kspTest(libs.hilt.compiler)

  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  androidTestImplementation(libs.androidx.test.core)
  androidTestImplementation(libs.androidx.test.runner)
  androidTestImplementation(libs.androidx.test.rules)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.androidx.test.espresso.core)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  androidTestImplementation(libs.junit)
}
