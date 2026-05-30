import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.maven.publish)
}

group = "space.zghoba"
version = libs.versions.project.get()

kotlin {
    jvm()
    androidLibrary {
        namespace = "space.zghoba.material-color-utilities-kotlin"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    iosArm64()
    iosSimulatorArm64()
    linuxX64()

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), name, version.toString())

    pom {
        name = "Material Color Utilities in Kotlin"
        description = "A lightweight Kotlin wrapper for Material Color Utilities (MCU), making dynamic theming."
        inceptionYear = "2026"
        url = "https://github.com/yaroslavzghoba/material-color-utilities-kotlin/"
        licenses {
            license {
                name = "MIT License"
                url = "https://raw.githubusercontent.com/yaroslavzghoba/material-color-utilities-kotlin/refs/heads/main/LICENSE"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "yaroslavzghoba"
                name = "Yaroslav Zghoba"
                email = "yaroslavzghoba@gmail.com"
                url = "https://github.com/yaroslavzghoba"
                organization = "yaroslavzghoba"
                organizationUrl = "https://github.com/yaroslavzghoba"
            }
        }
        scm {
            url = "https://github.com/yaroslavzghoba/material-color-utilities-kotlin/"
            connection = "scm:git:git://github.com/yaroslavzghoba/material-color-utilities-kotlin.git"
            developerConnection = "scm:git:ssh://github.com/yaroslavzghoba/material-color-utilities-kotlin.git"
        }
    }
}