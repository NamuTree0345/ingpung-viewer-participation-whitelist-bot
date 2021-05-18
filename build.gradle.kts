import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.5.0"
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = "xyz.namutree0345"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven("https://m2.dv8tion.net/releases")
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.github.KevinPriv:MojangAPI:1.0")
    implementation("net.dv8tion:JDA:4.2.1_253")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "xyz.namutree0345.ingpungWhitelist.Bootstrap"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
