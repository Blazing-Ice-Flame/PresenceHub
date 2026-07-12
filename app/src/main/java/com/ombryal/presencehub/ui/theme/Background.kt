package com.ombryal.presencehub.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

val GradientTop = Color(0xFF09090F)
val GradientBottom = Color(0xFF151522)

val PurpleGlow = Color(0xFF8B5CF6)
val VioletGlow = Color(0xFFA855F7)
val IndigoGlow = Color(0xFF6366F1)
val BlueGlow = Color(0xFF3B82F6)
val CyanGlow = Color(0xFF22D3EE)
val TealGlow = Color(0xFF14B8A6)
val PinkGlow = Color(0xFFF472B6)
val MagentaGlow = Color(0xFFEC4899)
val AmberGlow = Color(0xFFF59E0B)

val VignetteColor = Color(0x33000000)

@Composable
fun GlassmorphismBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        // Base vertical gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(GradientTop, GradientBottom),
                startY = 0f,
                endY = size.height
            )
        )

        // Bottom-left purple glow
        drawGlow(
            center = Offset(size.width * 0.15f, size.height * 0.82f),
            radius = size.minDimension * 0.65f,
            color = PurpleGlow.copy(alpha = 0.08f)
        )
        // Bottom-left violet
        drawGlow(
            center = Offset(size.width * 0.1f, size.height * 0.7f),
            radius = size.minDimension * 0.5f,
            color = VioletGlow.copy(alpha = 0.06f)
        )
        // Bottom-center indigo
        drawGlow(
            center = Offset(size.width * 0.45f, size.height * 0.88f),
            radius = size.minDimension * 0.75f,
            color = IndigoGlow.copy(alpha = 0.07f)
        )
        // Bottom-right pink
        drawGlow(
            center = Offset(size.width * 0.85f, size.height * 0.8f),
            radius = size.minDimension * 0.6f,
            color = PinkGlow.copy(alpha = 0.06f)
        )
        // Bottom-right magenta
        drawGlow(
            center = Offset(size.width * 0.9f, size.height * 0.72f),
            radius = size.minDimension * 0.55f,
            color = MagentaGlow.copy(alpha = 0.05f)
        )
        // Upper-left cyan
        drawGlow(
            center = Offset(size.width * 0.1f, size.height * 0.15f),
            radius = size.minDimension * 0.5f,
            color = CyanGlow.copy(alpha = 0.04f)
        )
        // Upper-right blue
        drawGlow(
            center = Offset(size.width * 0.85f, size.height * 0.2f),
            radius = size.minDimension * 0.5f,
            color = BlueGlow.copy(alpha = 0.05f)
        )
        // Center teal very subtle
        drawGlow(
            center = Offset(size.width * 0.6f, size.height * 0.5f),
            radius = size.minDimension * 0.4f,
            color = TealGlow.copy(alpha = 0.03f)
        )
        // Faint amber touch near middle-bottom
        drawGlow(
            center = Offset(size.width * 0.55f, size.height * 0.75f),
            radius = size.minDimension * 0.5f,
            color = AmberGlow.copy(alpha = 0.04f)
        )

        // Vignette
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color.Transparent, VignetteColor),
                center = Offset(size.width / 2f, size.height / 2f),
                radius = size.minDimension * 0.75f
            )
        )
    }
}

private fun DrawScope.drawGlow(center: Offset, radius: Float, color: Color) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(color, color.copy(alpha = 0f)),
            center = center,
            radius = radius
        ),
        radius = radius,
        center = center
    )
}
