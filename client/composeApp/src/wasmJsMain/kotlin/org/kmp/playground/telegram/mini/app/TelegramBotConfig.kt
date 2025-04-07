package org.kmp.playground.telegram.mini.app

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

private const val LOG_HTTP = "HTTP call"

private const val botToken =
    "7831723042:AAFdd4VY7bAkPVW5mC2U_v6i74Gvmbw4L-I" // Replace with your actual bot token
private const val miniAppUrl = "https://b11a-40-81-128-5.ngrok-free.app" // Replace with your Mini App URL
private const val telegramApiUrl = "https://api.telegram.org/bot$botToken"

val client = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })

    }
}

@Serializable
data class WebAppInfo(val url: String)

@Serializable
data class MenuButton(val type: String, val text: String, val web_app: WebAppInfo)

@Serializable
data class SetChatMenuButtonRequest(val menu_button: MenuButton)

fun telegramBotConfigureMenu() {
    CoroutineScope(Dispatchers.Default).launch {
        try {
            val requestBody = SetChatMenuButtonRequest(
                menu_button = MenuButton(
                    type = "web_app",
                    text = "Open KMP Mini App",
                    web_app = WebAppInfo(url = miniAppUrl)
                )
            )

            val response = client.post("$telegramApiUrl/setChatMenuButton") {
                contentType(ContentType.Application.Json)
                setBody(
                    requestBody
                )
            }
            println("Set Chat Menu Button Response: ${response.status}")
            println(response.body<String>())
        } catch (e: Exception) {
            println("Error setting chat menu button: ${e.message}")
        } finally {
            client.close()
        }
    }
}