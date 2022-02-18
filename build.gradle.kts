import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.arenagod.gradle.MybatisGenerator") version "1.4"
    kotlin("jvm") version "1.5.31"
    kotlin("plugin.spring") version "1.5.31"
    kotlin("plugin.jpa") version "1.5.31"
}

group = "com.rin-ats"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.6.3")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf:2.6.3")
    implementation("org.springframework.boot:spring-boot-starter-web:2.6.3")
    implementation("org.springframework.boot:spring-boot-starter-validation:2.6.3")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    runtimeOnly("mysql:mysql-connector-java:8.0.25")
    implementation("org.passay:passay:1.6.1")
    implementation("commons-codec:commons-codec:1.15")
    implementation("org.springframework.boot:spring-boot-starter-mail:2.6.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.3")
    testImplementation("org.springframework.security:spring-security-test:5.5.1")
    mybatisGenerator("org.mybatis.generator:mybatis-generator-core:1.4.0")
    implementation ("com.google.code.gson:gson:2.9.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}