package com.ombryal.presencehub.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

@Composable
fun GlassmorphismBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(GradientTop, GradientBottom),
                startY = 0f,
                endY = size.height
            )
        )

        drawGlow(
            center = Offset(size.width * 0.1f, size.height * 0.85f),
            radius = size.minDimension * 0.75f,
            color = PurpleGlow.copy(alpha = 0.12f)
        )
        drawGlow(
            center = Offset(size.width * 0.5f, size.height * 0.9f),
            radius = size.minDimension * 0.85f,
            color = IndigoGlow.copy(alpha = 0.10f)
        )
        drawGlow(
            center = Offset(size.width * 0.9f, size.height * 0.85f),
            radius = size.minDimension * 0.7f,
            color = PinkGlow.copy(alpha = 0.08f)
        )
        drawGlow(
            center = Offset(size.width * 0.3f, size.height * 0.25f),
            radius = size.minDimension * 0.6f,
            color = GrayPurpleGlow.copy(alpha = 0.06f)
        )
        drawGlow(
            center = Offset(size.width * 0.7f, size.height * 0.3f),
            radius = size.minDimension * 0.55f,
            color = GrayPurpleGlow.copy(alpha = 0.05f)
        )

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
