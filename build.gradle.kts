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
}

sonar {
    properties {
        property("sonar.projectKey", "CalculadoraFinanciera")
        property("sonar.projectName", "calculadoraFinanciera")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", System.getenv("SONAR_TOKEN"))
    }
}

tasks.test {
    useJUnitPlatform()
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