plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    application
}

group = "org.kmp.playground.telegram.mini.app"
version = "1.0.0"
application {
    mainClass.set("org.kmp.playground.telegram.mini.app.ApplicationKt")
//    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.kotlin.test.junit)
}