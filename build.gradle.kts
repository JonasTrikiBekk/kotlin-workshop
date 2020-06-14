import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion = "1.3.2"
val mockkVersion = "1.10.0"

plugins {
    application
    kotlin("jvm") version "1.3.61"
}

group = "no.bekk"
version = "1.0-SNAPSHOT"

repositories {
    maven(url = "https://dl.bintray.com/kotlin/ktor")
    mavenCentral()
}

sourceSets["main"].java.srcDir("src/main/kotlin")
sourceSets["test"].java.srcDir("src/main/test")

dependencies {
    implementation(kotlin("stdlib"))
    implementation("junit:junit:4.12")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")
    implementation("com.h2database:h2:1.4.200")
    implementation("com.zaxxer:HikariCP:3.2.0")
    testCompile("io.ktor:ktor-server-test-host:$ktorVersion")
    testCompile("io.mockk:mockk:$mockkVersion")
}

application {
    mainClassName = "no.bekk.kotlinworkshop.Task1Kt"
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += listOf("-XXLanguage:+InlineClasses")
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClassName
    }
    from(
        configurations.runtime.get().map {
            if (it.isDirectory) it else zipTree(it)
        }
    )
}
