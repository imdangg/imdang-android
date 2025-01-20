import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    namespace = "info.imdang.remote"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        minSdk = AppConfig.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("${rootProject.rootDir.absolutePath}/proguard-rules.pro")
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

    setFlavorDimensions(listOf("server"))

    productFlavors {
        create("dev") {
            dimension = "server"
            buildConfigField(
                "String",
                "API_SERVER",
                "\"${DevConfig.API_SERVER}\""
            )
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_ID",
                "\"${DevConfig.GOOGLE_WEB_CLIENT_ID}\""
            )
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_SECRET",
                "\"${localProperties["GOOGLE_WEB_CLIENT_SECRET_DEV"]}\""
            )
        }
        create("product") {
            dimension = "server"
            buildConfigField(
                "String",
                "API_SERVER",
                "\"${ProductConfig.API_SERVER}\""
            )
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_ID",
                "\"${ProductConfig.GOOGLE_WEB_CLIENT_ID}\""
            )
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_SECRET",
                "\"${localProperties["GOOGLE_WEB_CLIENT_SECRET"]}\""
            )
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // retrofit
    implementation(libs.retrofit)

    // paging
    implementation(libs.androidx.paging.common)
}
