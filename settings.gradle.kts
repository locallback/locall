pluginManagement {
    plugins {
        kotlin("jvm") version "2.0.21"
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "locall"
include("locall-framework")
include("locall-annotation")
include("locall-common")
include("locall-interpreter")
include("locall-native")
