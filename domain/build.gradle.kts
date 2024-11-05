plugins {
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    // hilt
    implementation(libs.hilt.core)

    // coroutines
    implementation(libs.coroutines)
}
