plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-core:5.3.22")
    implementation("org.springframework:spring-context:5.3.22")
    implementation("org.springframework:spring-test:5.3.22")
    implementation("org.projectlombok:lombok:1.18.24")
    implementation("org.junit.jupiter:junit-jupiter:5.9.0")
    annotationProcessor("org.projectlombok:lombok:1.18.24")
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("org.assertj:assertj-core:3.23.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
