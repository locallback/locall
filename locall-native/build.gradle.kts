plugins {
    id("java")
    kotlin("jvm")
}

group = "org.locallback"
version = "0.0.1-dev"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":locall-common"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.24.1"))
    implementation ("org.apache.logging.log4j:log4j-api:2.24.1")
    implementation ("org.apache.logging.log4j:log4j-core:2.24.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xuse-k2")
    }
}
