plugins {
    id("java")
}

group = "org.locallback"
version = "0.0.1-dev"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":locall-annotation"))

    implementation("io.github.classgraph:classgraph:4.8.177")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}