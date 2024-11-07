plugins {
    id("java")
    id("de.jjohannes.extra-java-module-info") version "0.14"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.thoughtworks.xstream:xstream:1.4.20")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

extraJavaModuleInfo {
    failOnMissingModuleInfo.set(false)
    automaticModule("com.thoughtworks.xstream:xstream", "xstream")
}

tasks.test {
    useJUnitPlatform()
}