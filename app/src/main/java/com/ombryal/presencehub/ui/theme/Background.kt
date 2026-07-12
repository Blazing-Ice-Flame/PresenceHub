package com.ombryal.presencehub.ui.theme

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Shader as AndroidShader
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.random.Random

// ---- Colour palette ----
private val RoyalPurple   = Color(0xFF8B5CF6)
private val DeepViolet    = Color(0xFF7C3AED)
private val Lavender      = Color(0xFFC084FC)
private val Indigo        = Color(0xFF6366F1)
private val ElectricBlue  = Color(0xFF3B82F6)
private val SkyBlue       = Color(0xFF60A5FA)
private val Cyan          = Color(0xFF22D3EE)
private val Turquoise     = Color(0xFF06B6D4)
private val Teal          = Color(0xFF14B8A6)
private val Emerald       = Color(0xFF10B981)
private val Lime          = Color(0xFF84CC16)
private val SoftAmber     = Color(0xFFF59E0B)
private val WarmOrange    = Color(0xFFFB923C)
private val RosePink      = Color(0xFFF472B6)
private val Magenta       = Color(0xFFEC4899)

private val BaseTop    = Color(0xFF08080C)
private val BaseMid    = Color(0xFF11131A)
private val BaseBottom = Color(0xFF171922)

@Composable
fun GlassmorphismBackground(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.fillMaxSize()) {
        // 1. Base vertical gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(BaseTop, BaseMid, BaseBottom),
                startY = 0f,
                endY = size.height
            )
        )

        // 2. Large ambient lights (layer 1)
        drawGlow(Offset(size.width * 0.25f, size.height * 0.15f), size.minDimension * 0.9f, RoyalPurple.copy(alpha = 0.08f))
        drawGlow(Offset(size.width * 0.75f, size.height * 0.10f), size.minDimension * 0.95f, ElectricBlue.copy(alpha = 0.07f))
        drawGlow(Offset(size.width * 0.10f, size.height * 0.70f), size.minDimension * 0.80f, Cyan.copy(alpha = 0.06f))
        drawGlow(Offset(size.width * 0.90f, size.height * 0.70f), size.minDimension * 0.85f, Magenta.copy(alpha = 0.06f))
        drawGlow(Offset(size.width * 0.50f, size.height * 0.80f), size.minDimension * 1.00f, Indigo.copy(alpha = 0.07f))
        drawGlow(Offset(size.width * 0.35f, size.height * 0.60f), size.minDimension * 0.75f, Turquoise.copy(alpha = 0.05f))
        drawGlow(Offset(size.width * 0.65f, size.height * 0.60f), size.minDimension * 0.75f, RosePink.copy(alpha = 0.05f))

        // 3. Medium accent lights (layer 2)
        drawGlow(Offset(size.width * 0.40f, size.height * 0.25f), size.minDimension * 0.60f, Lavender.copy(alpha = 0.07f))
        drawGlow(Offset(size.width * 0.60f, size.height * 0.25f), size.minDimension * 0.60f, SkyBlue.copy(alpha = 0.07f))
        drawGlow(Offset(size.width * 0.20f, size.height * 0.85f), size.minDimension * 0.55f, Teal.copy(alpha = 0.06f))
        drawGlow(Offset(size.width * 0.80f, size.height * 0.85f), size.minDimension * 0.55f, WarmOrange.copy(alpha = 0.06f))
        drawGlow(Offset(size.width * 0.50f, size.height * 0.45f), size.minDimension * 0.50f, DeepViolet.copy(alpha = 0.06f))
        drawGlow(Offset(size.width * 0.50f, size.height * 0.55f), size.minDimension * 0.50f, Emerald.copy(alpha = 0.05f))

        // 4. Fine ambient tints (layer 3)
        drawGlow(Offset(size.width * 0.30f, size.height * 0.40f), size.minDimension * 0.45f, Lime.copy(alpha = 0.04f))
        drawGlow(Offset(size.width * 0.70f, size.height * 0.40f), size.minDimension * 0.45f, SoftAmber.copy(alpha = 0.04f))
        drawGlow(Offset(size.width * 0.15f, size.height * 0.30f), size.minDimension * 0.50f, Cyan.copy(alpha = 0.04f))
        drawGlow(Offset(size.width * 0.85f, size.height * 0.30f), size.minDimension * 0.50f, Magenta.copy(alpha = 0.04f))

        // 5. Noise overlay
        drawNoiseOverlay(alpha = 0.025f)

        // 6. Vignette
        drawRect(
            brush = Brush.radialGradient(
                colors = listOf(Color.Transparent, Color(0x33000000)),
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

private fun DrawScope.drawNoiseOverlay(alpha: Float) {
    val noiseSize = 64
    val bitmap = Bitmap.createBitmap(noiseSize, noiseSize, Bitmap.Config.ARGB_8888)
    val random = Random(42)
    for (x in 0 until noiseSize) {
        for (y in 0 until noiseSize) {
            val gray = (random.nextFloat() * 255).toInt()
            val pixelAlpha = (alpha * 255).toInt().coerceIn(0, 255)
            bitmap.setPixel(x, y, android.graphics.Color.argb(pixelAlpha, gray, gray, gray))
        }
    }
    val shader = BitmapShader(bitmap, AndroidShader.TileMode.REPEAT, AndroidShader.TileMode.REPEAT)
    drawRect(
        brush = ShaderBrush(androidx.compose.ui.graphics.Shader(shader)),
        size = size
    )
}
