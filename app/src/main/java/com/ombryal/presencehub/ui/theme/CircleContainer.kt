package com.ombryal.presencehub.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Constants for the glass circle style
private val GlassCircleSurface = Color(0x1AFFFFFF)  // 10% white
private val GlassCircleBorder = Color(0x33FFFFFF)   // 20% white

@Composable
fun CircleContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(GlassCircleSurface, CircleShape)
            .border(1.dp, GlassCircleBorder, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
