plugins {
    id("java")
    id("java-library")
}

group = "org.locallback"
version = "0.0.1-dev"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":locall-annotation"))

    api("org.apache.commons:commons-lang3:3.17.0")
    api(platform("org.apache.logging.log4j:log4j-bom:2.24.1"))
    api ("org.apache.logging.log4j:log4j-api:2.24.1")
    api ("org.apache.logging.log4j:log4j-core:2.24.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}