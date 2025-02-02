import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.google.services)
    alias(libs.plugins.parcelize)
}

val keystorePropertiesFile = rootProject.file("key.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "info.imdang.imdang"
    compileSdk = AppConfig.COMPILE_SDK

    defaultConfig {
        applicationId = "info.imdang.imdang"
        minSdk = AppConfig.MIN_SDK
        targetSdk = AppConfig.TARGET_SDK
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties["storeFile"] ?: "../imdang_key_store.jks")
            storePassword = keystoreProperties["storePassword"] as String?
            keyAlias = keystoreProperties["keyAlias"] as String?
            keyPassword = keystoreProperties["keyPassword"] as String?
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
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

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    setFlavorDimensions(listOf("server"))

    productFlavors {
        create("dev") {
            dimension = "server"
            applicationIdSuffix = ".dev"
            addManifestPlaceholders(mapOf("KAKAO_NATIVE_KEY" to DevConfig.KAKAO_NATIVE_KEY))
            addManifestPlaceholders(mapOf("NAVER_CLIENT_ID" to DevConfig.NAVER_CLIENT_ID))
            buildConfigField(
                "String",
                "KAKAO_NATIVE_KEY",
                "\"${DevConfig.KAKAO_NATIVE_KEY}\""
            )
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_ID",
                "\"${DevConfig.GOOGLE_WEB_CLIENT_ID}\""
            )
            buildConfigField(
                "String",
                "NAVER_CLIENT_ID",
                "\"${DevConfig.NAVER_CLIENT_ID}\""
            )
            buildConfigField(
                "String",
                "KAKAO_ADDRESS_SEARCH_SERVER",
                "\"${DevConfig.KAKAO_ADDRESS_SEARCH_SERVER}\""
            )
        }
        create("product") {
            dimension = "server"
            addManifestPlaceholders(mapOf("KAKAO_NATIVE_KEY" to ProductConfig.KAKAO_NATIVE_KEY))
            addManifestPlaceholders(mapOf("NAVER_CLIENT_ID" to ProductConfig.NAVER_CLIENT_ID))
            buildConfigField(
                "String",
                "KAKAO_NATIVE_KEY",
                "\"${ProductConfig.KAKAO_NATIVE_KEY}\""
            )
            buildConfigField(
                "String",
                "GOOGLE_WEB_CLIENT_ID",
                "\"${ProductConfig.GOOGLE_WEB_CLIENT_ID}\""
            )
            buildConfigField(
                "String",
                "NAVER_CLIENT_ID",
                "\"${ProductConfig.NAVER_CLIENT_ID}\""
            )
            buildConfigField(
                "String",
                "KAKAO_ADDRESS_SEARCH_SERVER",
                "\"${DevConfig.KAKAO_ADDRESS_SEARCH_SERVER}\""
            )
        }
    }
}

dependencies {
    implementation(project(":component"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":remote"))
    implementation(project(":local"))

    // android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splash)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.camera.core)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.messaging)

    // google
    implementation(libs.play.services.auth)

    // kakao
    implementation(libs.kakao.login)

    // naver
    implementation(libs.naver.map)

    // glide
    implementation(libs.glide)

    // paging
    implementation(libs.androidx.paging.common)
    implementation(libs.androidx.paging.runtime.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
