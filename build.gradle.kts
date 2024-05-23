plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.17.1"
}

group = "ubb.taveeh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
//    maven("https://packages.jetbrains.team/maven/p/kds/kotlin-ds-maven")

}

dependencies {
    implementation(kotlin("stdlib", "1.9.22"))
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.6.0") {
        exclude(group = "xml-apis", module = "xml-apis")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")

//    implementation("org.jetbrains.kotlinx:kotlin-statistics-jvm:0.2.1")
//    implementation("org.jetbrains.kotlinx:kandy-api:0.6.0")
}
// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2024.1.1")
    type.set("IC") // Target IDE Platform
    plugins.set(listOf("org.jetbrains.kotlin"))
    plugins.add("com.intellij.java")
//    plugins.add("com.intellij.modules.python")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        sinceBuild.set("241")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
