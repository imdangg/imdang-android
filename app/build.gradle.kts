import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ktlint)
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "info.imdang.imdang"
    compileSdk = 34

    defaultConfig {
        applicationId = "info.imdang.imdang"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String?
            keyPassword = keystoreProperties["keyPassword"] as String?
            storeFile = keystoreProperties["storeFile"] as File?
            storePassword = keystoreProperties["storePassword"] as String?
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            addManifestPlaceholders(mapOf("KAKAO_NATIVE_KEY" to "b50fa405422b988a1b7a0d119d57db5b"))
            buildConfigField(
                "String",
                "KAKAO_NATIVE_KEY",
                "\"b50fa405422b988a1b7a0d119d57db5b\""
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            addManifestPlaceholders(mapOf("KAKAO_NATIVE_KEY" to "59e9eec5c9f86687f9bf55b7c251dc76"))
            buildConfigField(
                "String",
                "KAKAO_NATIVE_KEY",
                "\"59e9eec5c9f86687f9bf55b7c251dc76\""
            )
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    setFlavorDimensions(listOf("server"))

    productFlavors {
        create("dev") {
            dimension = "server"
            applicationIdSuffix = ".dev"
        }
        create("product") {
            dimension = "server"
        }
    }
}

dependencies {
    implementation(project(":component"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":remote"))

    // android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splash)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // kakao
    implementation(libs.kakao.login)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
