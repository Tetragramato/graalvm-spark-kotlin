
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.palantir.gradle.docker.DockerExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.31"
    id("com.github.johnrengelman.shadow") version "5.0.0"
    id("com.palantir.docker") version "0.22.1"
}

group = "com.tetragramato"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.sparkjava", "spark-core","2.8.0")
    implementation("org.slf4j", "slf4j-simple", "1.7.25")
    implementation(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val build: DefaultTask by tasks
val shadowJar = tasks["shadowJar"] as ShadowJar
build.dependsOn(shadowJar)

configure<DockerExtension> {
    name = "${project.group}/${rootProject.name}"
    files(shadowJar.outputs)
    buildArgs(mapOf(
        "PORT"   to  "4567",
        "NAME_APP" to "${rootProject.name}-${rootProject.version}-all.jar"
    ))
    pull(true)
    dependsOn(shadowJar, tasks["jar"])
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
    manifest {
        attributes(mapOf("Main-Class" to "com.tetragramato.ApplicationKt"))
    }
}
