plugins {
    kotlin(Plugins.MULTIPLATFORM) version Versions.KOTLIN
    kotlin(Plugins.SERIALIZATION) version Versions.KOTLIN
    `maven-publish`
}

group = Library.PACKAGE
version = Library.VERSION

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.KTOR_CORE)
                implementation(Dependencies.JSON)
                implementation(Dependencies.KTOR_SERIALIZATION)
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation(Dependencies.KTOR_CIO)
            }
        }

    }
}

applyDeploy()
