plugins {
    kotlin("jvm") version "1.3.72"
    application
}

val kotlin_version = "1.3.72"
group =  "com.vhl.ds"
version   = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
    maven( "https://dl.bintray.com/kyonifer/maven/")
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")

    implementation("org.nield:kotlin-statistics:1.2.1")

    implementation("com.github.doyaaaaaken:kotlin-csv-jvm:0.10.4")
    implementation("com.vhl.blackmo:kotlin-grass-jvm:0.2.1")

    //-- math  libs
    implementation("gov.nist.math:jama:1.0.3")
    implementation("com.kyonifer:koma-core-ejml:0.12")
    implementation("com.kyonifer:koma-plotting:0.12")
    implementation("org.apache.commons:commons-math3:3.0")

    implementation("no.tornado:tornadofx:1.7.17")

    testImplementation(kotlin("test"))
    testImplementation((kotlin("test-junit")))
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
}


application {
    mainClassName = "com.vhl.ds.MainAppKt"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
