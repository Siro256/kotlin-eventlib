import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.10"
    `maven-publish`
    signing
}

group = "dev.siro256"
version = "1.0.0-SNAPSHOT"
description = "A event library for Kotlin(JVM language)"
val projectLocation = "github.com/Sirrrrrro/kotlin-eventlib"

repositories {
    maven { url = uri("https://maven.siro256.dev/repository/maven-public/") }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.4.10")
}

tasks{
    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<ProcessResources> {
        filteringCharset = "UTF-8"
        from(projectDir) { include("LICENSE") }
    }

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets["main"].allSource)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = rootProject.group.toString()
            artifactId = rootProject.name
            version = rootProject.version.toString()

            from(components["java"])
            artifact(tasks["sourcesJar"])

            pom {
                name.set(rootProject.name)
                description.set("AAAAAAAA")
                url.set("https://$projectLocation")

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/mit-license.php")
                    }
                }

                developers {
                    developer {
                        id.set("Siro256")
                        name.set("Siro_256")
                        email.set("siro@siro256.dev")
                        url.set("https://github.com/Siro256")
                    }
                }

                scm {
                    connection.set("scm:git:git://$projectLocation.git")
                    developerConnection.set("scm:git:ssh://$projectLocation.git")
                    url.set("https://$projectLocation")
                }
            }
        }
    }

    repositories {
        maven {
            val releaseUrl = "https://maven.siro256.dev/repository/maven-releases/"
            val snapshotUrl = "https://maven.siro256.dev/repository/maven-snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT", true)) snapshotUrl else releaseUrl)

            credentials {
                username = System.getProperty("username")
                password = System.getProperty("password")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
