plugins {
    id("java")
    kotlin("jvm") version "2.0.21"
}

group = "org.locallback"
version = "0.0.1-dev"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":locall-annotation"))
    implementation(project(":locall-common"))

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

kotlin {
    jvmToolchain(21)
}




tasks.test {
    useJUnitPlatform()
}
