package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.sin

@Composable
fun AudioVisualizerWave(
    isSpeaking: Boolean,
    modifier: Modifier = Modifier,
    height: Dp = 64.dp,
    barCount: Int = 28,
    primaryColor: Color = MaterialTheme.colorScheme.primary,
    secondaryColor: Color = MaterialTheme.colorScheme.secondary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "audio_wave")
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val barWidth = (canvasWidth / (barCount * 1.6f)).coerceIn(4f, 16f)
            val spacing = (canvasWidth - (barCount * barWidth)) / (barCount - 1)

            val brush = Brush.verticalGradient(
                colors = listOf(primaryColor, secondaryColor)
            )

            for (i in 0 until barCount) {
                val x = i * (barWidth + spacing)
                val normalizedIndex = i.toFloat() / barCount

                val factor = if (isSpeaking) {
                    val angle = (phase + i * 22f) * (Math.PI / 180f).toFloat()
                    val sine = (sin(angle.toDouble()).toFloat() + 1f) / 2f
                    val bellCurve = (1f - 4f * (normalizedIndex - 0.5f) * (normalizedIndex - 0.5f)).coerceIn(0.2f, 1f)
                    (0.25f + sine * 0.75f * bellCurve).coerceIn(0.15f, 1f)
                } else {
                    val idleBell = (1f - 3f * (normalizedIndex - 0.5f) * (normalizedIndex - 0.5f)).coerceIn(0.1f, 0.4f)
                    idleBell * 0.35f
                }

                val barHeight = (canvasHeight * factor).coerceIn(6f, canvasHeight)
                val y = (canvasHeight - barHeight) / 2f

                drawRoundRect(
                    brush = brush,
                    topLeft = Offset(x, y),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(barWidth / 2f, barWidth / 2f)
                )
            }
        }
    }
}
