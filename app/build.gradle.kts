plugins {
    alias(libs.plugins.android.application)
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}
// Aplica el plugin google-services AQUÍ, fuera del bloque plugins
apply(plugin = "com.google.gms.google-services")

android {
    namespace = "com.example.practica2"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.practica2"
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    // Dependencias para Retrofit y Gson
    implementation(libs.retrofit) // Reemplaza con la versión más reciente
    implementation(libs.converter.gson) // Reemplaza con la versión más reciente
    implementation (libs.gson)
    // Dependencias para RecyclerView y CardView
    implementation(libs.androidx.recyclerview) // Reemplaza con la versión más reciente
    implementation(libs.androidx.cardview) // Reemplaza con la versión más reciente

    // Dependencias para Navigation Component o DrawerLayout
    implementation(libs.androidx.navigation.fragment.ktx) // Reemplaza con la versión más reciente
    implementation(libs.androidx.navigation.ui.ktx) // Reemplaza con la versión más reciente
    // O si prefieres DrawerLayout
    implementation(libs.androidx.drawerlayout) // Reemplaza con la versión más reciente

    // Dependencias para ViewModel y LiveData
    implementation(libs.androidx.lifecycle.viewmodel.ktx) // Reemplaza con la versión más reciente
    implementation(libs.androidx.lifecycle.livedata.ktx) // Reemplaza con la versión más reciente

    // Dependencia para encriptación Bcrypt

    implementation(libs.bcrypt)
    implementation(libs.firebase.storage.ktx) // Reemplaza con la versión más reciente

    implementation(libs.bcrypt) // Reemplaza con la versión más reciente


    // Pruebas
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth) // Autenticación Firebase
    implementation(libs.firebase.firestore) // Firestore

    // Dependencia de Picasso
    implementation (libs.picasso) // o la versión más reciente
    implementation (libs.material.v161)  // Esta es la dependencia para NavigationView
    implementation (libs.material.v1110) // o la versión más reciente

}