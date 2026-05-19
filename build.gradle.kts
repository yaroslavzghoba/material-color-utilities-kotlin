plugins {
    kotlin("jvm") version "2.3.20"
}

group = "space.zghoba"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(22)
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