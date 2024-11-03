plugins {
    id("java")
}

group = "org.locallback"
version = "0.0.1-dev"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":locall-common"))
    implementation(project(":locall-annotation"))
    implementation(project(":locall-interpreter"))

    implementation(platform("org.apache.logging.log4j:log4j-bom:2.24.1"))
    implementation ("org.apache.logging.log4j:log4j-api:2.24.1")
    implementation ("org.apache.logging.log4j:log4j-core:2.24.1")
    implementation("io.github.classgraph:classgraph:4.8.177")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}