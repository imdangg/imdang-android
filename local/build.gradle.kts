plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "info.imdang.local"
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
        }
        create("product") {
            dimension = "server"
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":remote"))

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // security-crypto
    implementation(libs.security.crypto)
}
