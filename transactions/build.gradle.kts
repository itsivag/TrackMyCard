plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
//            viewmodel
            implementation(libs.lifecycle.viewmodel.compose)

            // Koin
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)

            //logger
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.napier)
            //room
            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

//room {
//    schemaDirectory("$projectDir/schemas")
//}

//dependencies {
//    debugImplementation(compose.uiTooling)
//    ksp(libs.androidx.room.compiler)
//}


