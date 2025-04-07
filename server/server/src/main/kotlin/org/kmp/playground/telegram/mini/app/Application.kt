package org.kmp.playground.telegram.mini.app

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticFiles
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import java.io.File

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    routing {
        staticFiles("/", File("/Users/shadmanadman/KMP_Playground/KMP-TelegramMiniApp/client/composeApp/build/dist/wasmJs/productionExecutable"))
    }
}