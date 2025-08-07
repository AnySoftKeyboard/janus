plugins {
  id("com.android.library")
  id("org.jetbrains.kotlin.android")
  id("com.google.devtools.ksp")
}

android {
  namespace = "com.anysoftkeyboard.janus.database"
  compileSdk = 35

  defaultConfig {
    minSdk = 21
    targetSdk = 35

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }
  kotlin { jvmToolchain(17) }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.com.google.android.material)
  testImplementation(libs.junit)
  testImplementation(libs.robolectric)
  testImplementation(libs.androidx.test.core)
  testImplementation(libs.androidx.room.testing)
  testImplementation(libs.cash.turbine)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.androidx.test.espresso.core)

  // Room
  implementation(libs.androidx.room.runtime)
  implementation(libs.androidx.room.ktx)
  ksp(libs.androidx.room.compiler)
}
