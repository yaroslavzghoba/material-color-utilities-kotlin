plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.vanniktech.maven.publish)
}

group = "space.zghoba"
version = libs.versions.project.get()

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(17)
}

tasks.test {
    useJUnitPlatform()
}

tasks.register("printVersion") {
    description = "Print the current version of the project."
    doLast {
        println(project.version)
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