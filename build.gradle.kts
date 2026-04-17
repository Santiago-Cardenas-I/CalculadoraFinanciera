plugins {
    id("java")
    id("org.sonarqube") version "5.0.0.4638"
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.4"
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val junitVersion = "5.10.0"
val pitestJunit5Version = "1.2.1"

dependencies {

    // ============================
    // Aplicación
    // ============================

    implementation("org.springframework.boot:spring-boot-starter-web")

    // ============================
    // Testing
    // ============================

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.cucumber:cucumber-java:7.18.0")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:7.18.0")

    testImplementation("org.junit.platform:junit-platform-suite:1.10.2")
}

sonar {

    properties {
        property("sonar.java.binaries", "build/classes/java/main")
        property("sonar.projectKey", "CalculadoraFinanciera")
        property("sonar.projectName", "calculadoraFinanciera")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", System.getenv("SONAR_TOKEN"))
    }
}

tasks.test {
    useJUnitPlatform()
    systemProperty("file.encoding", "UTF-8")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

pitest {

    junit5PluginVersion.set(pitestJunit5Version)

    targetClasses.set(
        listOf("org.example.*")
    )

    targetTests.set(
        listOf("org.example.*Test")
    )

    threads.set(4)

    outputFormats.set(
        listOf("HTML", "XML")
    )
}

tasks.register<Test>("acceptanceTest") {
    useJUnitPlatform()
    description = "Runs Cucumber acceptance tests."
    group = "verification"

    useJUnitPlatform()

    testClassesDirs = sourceSets["test"].output.classesDirs
    classpath = sourceSets["test"].runtimeClasspath

    systemProperty("cucumber.plugin", "pretty")
}