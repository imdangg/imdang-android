plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "info.imdang.data"
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
        }
        create("product") {
            dimension = "server"
            buildConfigField(
                "String",
                "API_SERVER",
                "\"${ProductConfig.API_SERVER}\""
            )
        }
    }
}

dependencies {
    implementation(project(":domain"))

    // kotlin-stblib
    implementation(libs.kotlin.stblib)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // retrofit
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp3.logging)

    // room
}
