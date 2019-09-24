
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.31"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "com.tetragramato"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.sparkjava", "spark-core","2.8.0"){
        exclude("org.eclipse.jetty")
        exclude("org.eclipse.jetty.websocket")
    }
    implementation("org.eclipse.jetty", "jetty-server","9.4.19.v20190610")
    implementation("org.eclipse.jetty", "jetty-webapp","9.4.19.v20190610")
    implementation("org.eclipse.jetty.websocket", "websocket-server","9.4.19.v20190610")
    implementation("org.eclipse.jetty.websocket", "websocket-servlet","9.4.19.v20190610")
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

tasks.withType<ShadowJar> {
    mergeServiceFiles()
    manifest {
        attributes(mapOf("Main-Class" to "com.tetragramato.ApplicationKt"))
    }
}
