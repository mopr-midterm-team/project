plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.repo_midterm"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.repo_midterm"
        minSdk = 24
        targetSdk = 35
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packagingOptions {
        exclude("META-INF/license.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/LICENSE")
        exclude("META-INF/NOTICE")
        exclude("META-INF/DEPENDENCIES")
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.retrofit)
    implementation(libs.gsonConverter)
    implementation(libs.okhttpLoggingInterceptor)
    implementation(libs.gson)
    implementation(libs.threetenbp)
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.0")
    implementation("org.springframework:spring-web:5.3.23")
    implementation("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    implementation(libs.circleimageview)
    implementation(libs.coil.compose)


    //Gradle Image Loader
    implementation ("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.14.2")
    implementation("com.google.android.material:material:1.11.0")



}