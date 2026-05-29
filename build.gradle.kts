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

// 1. Centralizamos las versiones
val junitVersion = "5.10.0"
val pitestJunit5Version = "1.2.1"
val cucumberVersion = "7.18.0"
val junitPlatformSuiteVersion = "1.10.2"
val serenityVersion = "3.9.8"
val seleniumVersion = "4.14.1"

// 2. Agrupamos las dependencias
dependencies {

    // --- DEPENDENCIAS DE PRODUCCIÓN ---
    implementation("org.springframework.boot:spring-boot-starter-web")

    // --- DEPENDENCIAS DE PRUEBAS (testImplementation) ---
    testImplementation(platform("org.junit:junit-bom:${junitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.junit.platform:junit-platform-suite:${junitPlatformSuiteVersion}")

    // Cucumber
    testImplementation("io.cucumber:cucumber-java:${cucumberVersion}")
    testImplementation("io.cucumber:cucumber-junit-platform-engine:${cucumberVersion}")

    // Serenity BDD
    testImplementation("net.serenity-bdd:serenity-core:${serenityVersion}")
    testImplementation("net.serenity-bdd:serenity-cucumber:${serenityVersion}")

    // Selenium
    testImplementation("org.seleniumhq.selenium:selenium-java:${seleniumVersion}")

    // --- DEPENDENCIAS DE EJECUCIÓN DE PRUEBAS (testRuntimeOnly) ---
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

sonar {

    properties {
        property ("sonar.java.binaries", "build/classes/java/main")
        property("sonar.projectKey", "CalculadoraFinanciera")
        property("sonar.projectName", "calculadoraFinanciera")
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.token", System.getenv("SONAR_TOKEN"))

        property("sonar.qualitygate.wait", "true")
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

    useClasspathFile.set(true)

    targetClasses.set(listOf("org.example.*"))
    targetTests.set(listOf("org.example.*Test"))

    threads.set(4)

    outputFormats.set(listOf("HTML", "XML"))
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