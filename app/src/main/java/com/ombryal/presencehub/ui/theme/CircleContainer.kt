package com.ombryal.presencehub.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.ombryal.presencehub.ui.settings.ThemeMode

@Composable
fun CircleContainer(
    modifier: Modifier = Modifier,
    themeMode: ThemeMode,
    content: @Composable () -> Unit
) {
    val (surface, border) = when (themeMode) {
        ThemeMode.LIGHT -> LightCircleSurface to LightCircleBorder
        else -> DarkCircleSurface to DarkCircleBorder   // DARK, GLASS, SYSTEM (assuming dark default)
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(surface, CircleShape)
            .border(1.dp, border, CircleShape),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        content()
    }
}
