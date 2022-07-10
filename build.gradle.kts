plugins {
    `maven-publish`
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}

group = "com.entiv"
version = "1.0"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "Paper"
        url = uri("https://papermc.io/repo/repository/maven-public/")
    }

    maven {
        name = "PlaceholderAPI"
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }

    maven {
        name = "WorldEdit"
        url = uri("https://maven.enginehub.org/repo/")
    }

    maven {
        name = "purpur"
        url = uri("https://repo.purpurmc.org/snapshots")
    }
}

dependencies {
    implementation("com.entiv:insekicore:1.0.8")
    compileOnly(kotlin("stdlib"))

    compileOnly("me.clip:placeholderapi:2.11.0")
//    compileOnly("com.djrapitops:plan-api:5.2-R0.1")
//    compileOnly("com.zaxxer:HikariCP:5.0.1")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7.1")
    implementation(platform("com.intellectualsites.bom:bom-1.18.x:1.5"))
    compileOnly("com.plotsquared:PlotSquared-Core:6.9.0")
    compileOnly("org.purpurmc.purpur", "purpur-api", "1.19-R0.1-SNAPSHOT")
//    compileOnly("org.spigotmc:spigot:1.19-R0.1-SNAPSHOT:remapped-mojang")
    compileOnly(fileTree("libs"))
}

tasks {

    shadowJar {
        project.findProperty("outputPath")?.let {
            destinationDirectory.set(file(it.toString()))
        }

        archiveFileName.set("${project.name}-${project.version}.jar")
        relocate("com.entiv.core", "${project.group}.lib")
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    processResources {
        filesMatching("plugin.yml") {
            expand(
                "name" to project.name,
                "version" to project.version
            )
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}