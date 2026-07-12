package com.ombryal.presencehub.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CircleContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val primary = MaterialTheme.colorScheme.primary
    // Tinted glass surface – uses theme's primary color at 15%
    val surface = primary.copy(alpha = 0.15f)
    // Crisp border – primary at 30%
    val border = primary.copy(alpha = 0.30f)

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
