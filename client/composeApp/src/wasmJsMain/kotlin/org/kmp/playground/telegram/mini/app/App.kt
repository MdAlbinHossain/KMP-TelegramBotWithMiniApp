package org.kmp.playground.telegram.mini.app


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.skia.FontWeight
import kotlin.random.Random


@Composable
fun App() {
    // Call the configureMenu function to set the chat menu button
    telegramBotConfigureMenu()

    MaterialTheme {
        Box(Modifier.fillMaxSize()) {
            BouncingDots()
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "KMP",
                    fontSize = 120.sp,
                    color = Color(0xffc64885),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Hello from KMP\nThis is a simple Telegram Bot/MiniApp with KMP",
                    fontSize = 34.sp,
                    color = Color.White,
                    lineHeight = 50.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}


@Composable
fun BouncingDots(dotCount: Int = 150) {
    val screenWidth = 2000
    val screenHeight = 4000

    data class Dot(
        val x: Animatable<Float, AnimationVector1D>,
        val y: Animatable<Float, AnimationVector1D>,
        var dx: Float,
        var dy: Float
    )

    val dots = remember {
        List(dotCount) {
            Dot(
                x = Animatable(Random.nextFloat() * screenWidth),
                y = Animatable(Random.nextFloat() * screenHeight),
                dx = (Random.nextFloat() - 0.5f) * 4f,
                dy = (Random.nextFloat() - 0.5f) * 4f
            )
        }
    }

    // Launch animation loops
    LaunchedEffect(Unit) {
        coroutineScope {
            dots.forEach { dot ->
                launch {
                    while (true) {
                        dot.x.snapTo(dot.x.value + dot.dx)
                        dot.y.snapTo(dot.y.value + dot.dy)

                        // Bounce off edges
                        if (dot.x.value <= 0 || dot.x.value >= screenWidth) dot.dx *= -1
                        if (dot.y.value <= 0 || dot.y.value >= screenHeight) dot.dy *= -1

                        delay(26) // ~60 FPS
                    }
                }
            }
        }
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF4DA0B0), Color(0xFFD39D38)),
                startY = 0f,
                endY = size.height
            ),
            size = size
        )
        dots.forEach { dot ->
            drawCircle(
                color = Color.White.copy(alpha = 0.6f),
                radius = 5f,
                center = Offset(dot.x.value, dot.y.value)
            )
        }
    }
}