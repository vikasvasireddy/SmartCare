plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    kotlin("plugin.jpa") version "2.0.20"
    id("jacoco")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Use Java 21 consistently
    }
}

allprojects {
    group = "io.vikasvasireddy"
    version = "1.0.0"

    repositories {
        mavenCentral()
        maven { url = uri("https://repo.spring.io/milestone") }
    }
}

// Root project dependencies
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

// Shared configuration for all submodules
subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-spring")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21)) // Use Java 21 in submodules
        }
    }

    dependencies {
        // Common Kotlin dependencies
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib")

        // Shared test dependencies
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            exclude(group = "org.mockito") // Optional: Exclude mockito if unused
        }
        testImplementation("io.projectreactor:reactor-test")
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjvm-default=all")
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
            }
        }
    }
    tasks.register("wrapper", Wrapper::class) {
        gradleVersion = "8.11.1"
        distributionType = Wrapper.DistributionType.ALL
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.0")
    }
}


jacoco {
    toolVersion = "0.8.10"
    reportsDirectory.set(file("$buildDir/customJacocoReportDir"))
}
