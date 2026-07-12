package com.ombryal.presencehub.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ombryal.presencehub.ui.settings.ThemeMode

@Composable
fun CircleContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val themeMode = LocalThemeMode.current
    val (surface, border) = when (themeMode) {
        ThemeMode.LIGHT -> LightCircleSurface to LightCircleBorder
        else -> DarkCircleSurface to DarkCircleBorder
    }

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(surface, CircleShape)
            .border(1.dp, border, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
