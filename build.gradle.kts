plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.dylanwraith"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.okta.spring:okta-spring-boot-starter:3.0.5")
	implementation("com.mysql:mysql-connector-j:8.2.0")
	implementation("org.flywaydb:flyway-core:11.2.0")
	implementation("org.flywaydb:flyway-mysql:11.2.0")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.projectlombok:lombok:1.18.24")
	implementation("org.hibernate.validator:hibernate-validator:7.0.1.Final")
	implementation("jakarta.validation:jakarta.validation-api:3.0.0")

	annotationProcessor("org.projectlombok:lombok:1.18.24")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
